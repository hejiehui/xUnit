xUnit
=====

A common system builder

# 简介
Xross unit 编辑器是一个灵活的系统构建器。

在Eclipse里面所见即所得的方式通过构建流程图来构建系统

运行时，其方式是通过对Context的处理来完成

# 优点
1. 一图胜千言
1. 超越传统的开发模式，从代码和配置的汪洋里解脱出来
1. 模型归模型，代码归代码，查看代码仅需双击进入
1. 可以在应用或构建单元层次上面配置参数

![overview](https://github.com/hejiehui/xUnit/blob/master/doc/overview.png) 

# 添加依赖
[参考样例POM](https://github.com/hejiehui/xUnit/blob/master/com.xrosstools.xunit.sample/pom.xml)
Depenency

		<dependency>
			<groupId>com.xrosstools</groupId>
			<artifactId>xunit</artifactId>
			<version>0.9.0-SNAPSHOT</version>
		</dependency>

repository

	<repositories>
		<repository>
			<id>xtools-repo</id>
			<url>https://raw.github.com/hejiehui/xtools-repo/mvn-repo/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>
# 组件
## 行为组件
### processor
对Context进行处理，但没有返回值

    public interface Processor extends Unit{
    	void process(Context ctx);
    }

![processor](https://github.com/hejiehui/xUnit/blob/master/doc/processor.PNG)

### converter
对传入的context进行转换，转变为新的Context。也可以返回原来的Context

    public interface Converter extends Unit{
    	Context convert(Context inputCtx);
    }

![converter](https://github.com/hejiehui/xUnit/blob/master/doc/converter.PNG)

### validator
对Context进行true或者false的判断

    public interface Validator extends Unit{
    	boolean validate(Context ctx);
    }

### locator
对Context进行分类的判断。支持缺省值

    public interface Locator extends Unit{
    	String locate(Context ctx);
    	void setDefaultKey(String key);
    	String getDefaultKey();
    }

## 结构组件
### chain
对内部的unit顺序调度处理
![chain](https://github.com/hejiehui/xUnit/blob/master/doc/chain.PNG)

### if-else
通过Validator决定调用内部那个unit
![ifelse](https://github.com/hejiehui/xUnit/blob/master/doc/if-else.PNG)

### branch
通过Locator判断调度内部那个unit
![branch](https://github.com/hejiehui/xUnit/blob/master/doc/branch.PNG)

### while
通过Validator判断的while结构
![while](https://github.com/hejiehui/xUnit/blob/master/doc/while.PNG)

### do while loop
通过Validator判断的do while结构
![dowhile](https://github.com/hejiehui/xUnit/blob/master/doc/do-while.PNG)

### decorator
在操作前后处理

    public interface Decorator extends Adapter {
    	/**
	     * Extends this method to provide decoration before decorated unit executed
	     * @param ctx
	     */
	    void before(Context ctx);
    
    	/**
	     * Extends this method to provide decoration after decorated unit executed
	     * @param ctx
	     */
	    void after(Context ctx);
    }

![decorator](https://github.com/hejiehui/xUnit/blob/master/doc/decorator.PNG)

### adapter
将某种unit的行为转换为另一种

    public interface Adapter extends Unit{
    	void setUnit(Unit unit);
    }

![adapter](https://github.com/hejiehui/xUnit/blob/master/doc/adaptor.PNG)

# 编辑方法
直接选择需要的组件，点击编辑器特定的区域

点击和对象组合 – E.g. Validator + Unit = if/else structure

## 构建蓝图
你可以一直和PM, PD, QA一起优化修改讨论

![top](https://github.com/hejiehui/xUnit/blob/master/doc/top_design.png)

## 创建组件单元
函数式接口易于实现和测试

![impl](https://github.com/hejiehui/xUnit/blob/master/doc/implement.png)

## 配置
结合代码和系统蓝图，配置参数

![assigne](https://github.com/hejiehui/xUnit/blob/master/doc/assign.png)

## 运行
通过factory load模型文件，创建Contex并调用指定的unit

![run](https://github.com/hejiehui/xUnit/blob/master/doc/run.png)

# 实际案例
## 简单模型
![uc1](https://github.com/hejiehui/xUnit/blob/master/doc/uc1.png)

## 同一模型文件案例
同一文件内部可以实现主图和子图引用

### 主图
![entry](https://github.com/hejiehui/xUnit/blob/master/doc/uc_entry.png)

### 子图1
![biz](https://github.com/hejiehui/xUnit/blob/master/doc/uc_biz.png)
![assign](https://github.com/hejiehui/xUnit/blob/master/doc/us_biz_assign.png)

### 子图2
![resp](https://github.com/hejiehui/xUnit/blob/master/doc/uc_response.png)
![assign](https://github.com/hejiehui/xUnit/blob/master/doc/uc_response_assign.png)

## 不同文件文件案例
这个是用户自创的方式，未来系统缺省会提供缺省实现
主图和子图在不同文件，通过通用的dispatcher来实现结合

### 案例1
![uc1](https://github.com/hejiehui/xUnit/blob/master/doc/uc_user_ref.png)

### 案例2
![uc2](https://github.com/hejiehui/xUnit/blob/master/doc/uc_station_ref.png)
