import cn.edu.zjut.BadWordDecorator;
import cn.edu.zjut.WordReplaceWriter;
import cn.edu.zjut.exception.FilePathNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/13 20:01
 * @Description: 该类用于 测试类
 */
public class testWordFilter {
    @Test
    @DisplayName("wordFilter")
    public void wordFilterTest() throws FilePathNullException {

        String badWords="李华是傻逼";

        WordReplaceWriter wordReplaceWriter=new WordReplaceWriter("badwords.txt");
        System.out.println(wordReplaceWriter.filter(badWords));
        //替换敏感词
        BadWordDecorator badWordDecorator=new BadWordDecorator(wordReplaceWriter,'!');
        System.out.println(badWordDecorator.filter(badWords));
        //添加新的敏感词
        BadWordDecorator badWordDecorator2=new BadWordDecorator(badWordDecorator,"badwords2.txt");
        System.out.println(badWordDecorator2.filter(badWords));
    }
}
