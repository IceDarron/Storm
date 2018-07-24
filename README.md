Storm-Kafka
===

project本身为空的maven模块，下面包含多个子模块：

+ storm模块

+ web-app模块

### storm
包含两个实例：

demo为无任何逻辑的拓扑示例。

wordcount为单词切分统计拓扑。


### storm的八种Grouping策略

1）shuffleGrouping（随机分组）

2）fieldsGrouping（按照字段分组，在这里即是同一个单词只能发送给一个Bolt）

3）allGrouping（广播发送，即每一个Tuple，每一个Bolt都会收到）

4）globalGrouping（全局分组，将Tuple分配到task id值最低的task里面）

5）noneGrouping（随机分派）

6）directGrouping（直接分组，指定Tuple与Bolt的对应发送关系）

7）Local or shuffle Grouping

8）customGrouping （自定义的Grouping）


### 参考文献
https://github.com/IceDarron/Note/blob/master/about_storm.md



