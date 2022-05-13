package cn.edu.zjut;

import cn.edu.zjut.exception.FilePathNullException;
import cn.edu.zjut.util.BCConvert;
import cn.edu.zjut.util.FilterSet;
import cn.edu.zjut.util.WordNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.*;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/13 12:43
 * @Description: 该类用于 文本替换类
 */
@Data
@NoArgsConstructor
public class WordReplaceWriter extends ReplaceWriter{

    /**
     * 存储首字
     */
    private static final FilterSet set = new FilterSet();
    /**
     * 存储节点
     */
    private static final Map<Integer, WordNode> nodes = new HashMap<Integer, WordNode>(1024, 1);
    /**
     * 停顿词
     */
    private static final Set<Integer> stopwdSet = new HashSet<>();

    static {
        try {
            init();
        } catch (Exception e) {
            // 加载失败
        }
    }

    /**
     * 初始化
     * @author zk
     * @date 2022/5/13 18:40
     */
    private static void init() {
        // 获取停顿词
        addStopWord(readWordFromFile("sensitiveWordsSplitCode.txt"));
    }
    /**
     * 从recourse文件夹下按行读取文件并返回List集合
     * @author zk
     * @date 2022/5/13 18:41
 	 * @param path
	 * @return java.util.List<java.lang.String>
     */
    private static List<String> readWordFromFile(String path) {
        List<String> words;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(WordReplaceWriter.class.getClassLoader().getResourceAsStream(path)));
            words = new ArrayList<String>(1200);
            for (String buf = ""; (buf = br.readLine()) != null;) {
                if (buf == null || "".equals(buf)) {
                    continue;
                }
                words.add(buf);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
        }
        return words;
    }
    /**
     * 判断集合是否为空
     * @author zk
     * @date 2022/5/13 18:37
     * @param col
     * @return boolean
     */
    public static <T> boolean isEmpty(final Collection<T> col) {
        return col == null || col.isEmpty();
    }
    /**
     * 增加停顿词
     * @author zk
     * @date 2022/5/13 18:39
 	 * @param words
     */
    private static void addStopWord(final List<String> words) {
        if (!isEmpty(words)) {
            char[] chs;
            for (String curr : words) {
                chs = curr.toCharArray();
                for (char c : chs) {
                    stopwdSet.add(charConvert(c));
                }
            }
        }
    }
    /**
     * 大/小写,全/半角转换
     * @author zk
     * @date 2022/5/13 18:39
 	 * @param src
	 * @return int
     */
    private static int charConvert(char src) {
        int r = BCConvert.qj2bj(src);
        return (r >= 'A' && r <= 'Z') ? r + 32 : r;
    }

    /**
     * 敏感词替换后的词
     */
    protected char SIGN='@';
    /**
     * 敏感词文件路径
     */
    private String sensativeWordPath;

    @Override
    public String filter(String str) throws FilePathNullException {
        if(sensativeWordPath==null){
            throw new FilePathNullException("敏感词文本文件未加载");
        }
        return doFilter(str);
    }

    @Override
    public void changeSIGN(char newSIGN){
        SIGN=newSIGN;
    }

    @Override
    protected void addSensativeWords(String path) {
        addSensitiveWord(readWordFromFile(path));
    }

    /**
     * 增加敏感词,DFA算法
     * @author zk
     * @date 2022/5/13 18:46
 	 * @param words
     */
    private void addSensitiveWord(final List<String> words) {
        if(isEmpty(words)){
            System.out.println("words为空");
        }
        if (!isEmpty(words)) {
            char[] chs;
            int fchar;
            int lastIndex;
            WordNode fnode; // 首字母节点
            for (String curr : words) {
                chs = curr.toCharArray();
                fchar = charConvert(chs[0]);
                if (!set.contains(fchar)) {// 没有首字定义
                    set.add(fchar);// 首字标志位 可重复add,反正判断了，不重复了
                    fnode = new WordNode(fchar, chs.length == 1);
                    nodes.put(fchar, fnode);
                } else {
                    fnode = nodes.get(fchar);
                    if (!fnode.isLast() && chs.length == 1) {
                        fnode.setLast(true);
                    }
                }
                lastIndex = chs.length - 1;
                for (int i = 1; i < chs.length; i++) {
                    fnode = fnode.addIfNoExist(charConvert(chs[i]), i == lastIndex);
                }
            }
        }
    }

    /**
     * DFA算法敏感词过滤
     * @param src
     * @return
     */
    private final String doFilter(final String src) {
        if (set != null && nodes != null) {
            char[] chs = src.toCharArray();
            int length = chs.length;
            int currc; // 当前检查的字符
            int cpcurrc; // 当前检查字符的备份
            int k;
            WordNode node;
            for (int i = 0; i < length; i++) {
                currc = charConvert(chs[i]);
                if (!set.contains(currc)) {
                    continue;
                }
                node = nodes.get(currc);// 日 2
                if (node == null) {
                    continue;
                }
                boolean couldMark = false;
                int markNum = -1;
                if (node.isLast()) {// 单字匹配（日）
                    couldMark = true;
                    markNum = 0;
                }
                // 继续匹配（日你/日你妹），以长的优先
                // 你-3 妹-4 夫-5
                k = i;
                cpcurrc = currc; // 当前字符的拷贝
                for (; ++k < length;) {
                    int temp = charConvert(chs[k]);
                    if (temp == cpcurrc) {
                        continue;
                    }
                    if (stopwdSet != null && stopwdSet.contains(temp)) {
                        continue;
                    }
                    node = node.querySub(temp);
                    if (node == null)// 没有了
                    {
                        break;
                    }
                    if (node.isLast()) {
                        couldMark = true;
                        markNum = k - i;// 3-2
                    }
                    cpcurrc = temp;
                }
                if (couldMark) {
                    for (k = 0; k <= markNum; k++) {
                        chs[k + i] = SIGN;
                    }
                    i = i + markNum;
                }
            }
            return new String(chs);
        }
        return src;
    }

    public WordReplaceWriter(String sensativeWordPath) {
        this(sensativeWordPath,'*');
    }

    public WordReplaceWriter(String sensativeWordPath, char SIGN) {
        this.sensativeWordPath = sensativeWordPath;
        this.SIGN = SIGN;
        addSensitiveWord(readWordFromFile(sensativeWordPath));
    }
}
