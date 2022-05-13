# 一、设计问题

股票的价格随着时间会在一定范围内波动，对于每个股票，股票分析软件提供多种指标分析，如分时图，K线图等，以辅助投资者进行投资决策。分时图是将每分钟的股票价格连起来的折线图；K线图反应每天股票的开盘价、收盘价、最低价和最高价（收盘价高于开盘价用红线绘制，反之用绿线绘制，若收盘价等于开盘价用白线绘制）：根据情况，也可能需要增加其他类型的指标分析报告。请选用适当的设计模式，编写一个股票行情分析软件，随着时间的推移和股票价格的变动，实现各种指标的动态更新（要求至少实现一个股票的分时图和K线图：为降低编程工作量，K线图可以用开盘价、收盘价、最高价、最低价取代。）。按实验一的要求提交实验报告。

提示：股价变动用随机数模拟：java.util.Random，用一个线程模拟股票行情数据的产生。

# 二、问题分析与模式选用

对设计问题进行分析，选择需要应用的设计模式并说明理由，画出相应设计模式的UML类图（可用Rational rose、viso或其他绘图软件）。

UML类图如下

![](media/72471dae2c1b337edc9b0fbec93b0d85.png)

设计模式采用观察者模式,因为具体主题和具体观察者是松耦合关系。由于主题(Subject)接口仅仅依赖于观察者(Observer)接口,因此具体主题只是知道它的观察者是实现观察者(Observer)接口的某个类的实例,但不需要知道具体是哪个类。同样,由于观察者仅仅依赖于主题(Subject)接口,因此具体观察者只是知道它依赖的主题是实现主题(Subject)接口的某个类的实例,但不需要知道具体是哪个类。其次,观察者模式满足“开-闭原则”。主题(Subject)接口仅仅依赖于观察者(Observer)接口,这样,就可以让创建具体主题的类也仅仅是依赖于观察者(Observer)接口,因此如果增加新的实现观察者(Observer)接口的类,不必修改创建具体主题的类的代码。同样,创建具体观察者的类仅仅依赖于主题(Observer)接口,如果增加新的实现主题(Subject)接口的类,也不必修改创建具体观察者类的代码。

# 三、设计方案

给出对设计问题的具体设计方案，设计方案的UML类图，并加以说明。

UML类图见上文,项目结构如下

![](media/e7ef854e02c05ce4d2fed5ef38843c00.png)

1.  首先创建接口Observer和Subject,然后创建其实现类StockExchangerCenter,KLineDiagramObserver和LineChartObserver,分别对应股票投资中心和两个观察者,K线图和折线图观察者
2.  创建Stock类作为股票的基础数据, KStockAtomicData类作为K线图所需的原始数据，包括开盘价收盘价最高价最低价等等, KStockAllData类作为维护K线图的总数据。
3.  创建springboot作为可视化web服务器，采用Websocket作为前后端数据交互的桥梁，Thymeleaf作为网页模板，Echarts绘制股票图表
4.  在ObserverModeApplication类的构造函数中创建线程来模拟股票交易

# 四、运行结果及效果分析

给出主要的运行截图，分析设计达到的效果。

启动ObserverModeApplication的main函数来启动springboot，在浏览器中输入地址 <http://localhost:8000/>

![](media/2ae84ced989f42d4cfed92d020d11738.png)

主要有两个按钮分别对应K线图和折线图

![](media/80037b5fde1e215d496ab0016322724d.png)

![](media/103a33550393d3af0002d809009ba69c.png)

图表每秒接受后端发送的数据并把它显示在网页上

![](media/968b5928af2ac062eca8e60a453b8c10.gif)

GIF图演示如上

# 五、关键代码（算法）及其说明

给出项目实现中的关键代码和说明（通过注释），涉及算法的给出算法描述。

1.  基础接口类Observer实现如下

![](media/1173c6711e58e8fd7831a2b554d75922.png)

1.  其继承类LineChartObserver实现如下

    ![](media/23ea2a273ea14af47d5ae73579e79dd7.png)

2.  K线图观察者实现如下

    ![](media/555806a322b2350528f1c0a65be9307e.png)

    其中用到了存储K线图所需数据的类KStockAllData,如下![](media/a5769d97fc85f5bb0a7e67b35ef859c5.png)

    其中用到了KStockAtomicData作为存储开盘价收盘价最高价最低价的K线图原子数据存储类,如下

    ![](media/491befa00a6b8bcf830cba36692d1a1e.png)

    Stock类实现如下

    ![](media/34a557db98dcf2311bb998ce687acb8a.png)

3.  基础接口Subject类实现如下

    ![](media/4da1441166c6a2ecf5ddba87fc493329.png)

4.  websocket服务端实现如下

![](media/4881426bfe68ebfea2f34ac52666f3d4.png)

![](media/0f9ea2653bf68926b2161f59110ea86f.png)

![](media/5a31f8587ecbec4f0b52694b943ca354.png)

实现该服务请求需要完成以下两个类

![](media/cf96fc5a06a4f0e1d8d6c084b3ead821.png)

![](media/21a4518558b8dfe4e7baa23e5c7d1bae.png)

1.  多线程实现如下

    ![](media/bd45930408fd5f0b68fc84d423f87b42.png)
