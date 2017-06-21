xUnit
=====

A common system builder

# 简介
Xross unit 编辑器是一个灵活的系统构建器。用户可以在Eclipse里以所见即所得的方式快速构建控制流程，打造系统的骨架。

流程图可以直观的理解和维护系统，点击流程节点可以直接打开节点对应的代码。让你随时随地掌握系统的整体结构和具体细节。

Xross Unit尤其适合重构老系统。特别是跨语言的重构，通过Xross Unit可以高效的让对老系统，老语言熟悉的开发人员高效的把对老系统的理解转换为新系统。

# 优点
1. 一图胜千言
1. 超越传统的开发模式，从代码和配置的汪洋里解脱出来
1. 模型归模型，代码归代码，查看代码仅需双击进入
1. 可以在应用或构建单元层次上面配置参数

![overview](https://github.com/hejiehui/xUnit/blob/master/doc/overview.png) 

# 集成说明
[参考样例POM](https://github.com/hejiehui/xUnit/blob/master/com.xrosstools.xunit.sample/pom.xml)

Depenency

	<dependency>
		<groupId>com.xrosstools</groupId>
		<artifactId>xunit</artifactId>
		<version>0.9.2</version>
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

# Demo project
[Demo](https://github.com/hejiehui/xUnit/tree/master/com.xrosstools.xunit.sample)

# 设计思路
任何一个系统，模块，处理单元的工作模式都可以简化为IPO模型，即把一定的输入通过模块定义的动作，转化为一定的输出：

input --> process --> output。

依据这种抽象，xunt把输入数据/输出数据抽象为Context接口。

	package com.xrosstools.xunit;

	public interface Context {
	
	}

把输入到输出之间的转化行为定义为Converter接口。由于Converter接口定义了行为，因此又称为行为组件。Converter的工作是把输入Context转化为输出Context。

如果输入与输出是相同的具体类型（Context的子类），则Converter接口可以简化为Processor接口。即仅仅接收输入Context，但没有返回值。Processor一般对输入的Context的内部属性做处理。

将不同的Converter和Processor依次串联起来，让Context从第一个处理单元一直流动到最后一个处理单元，可以完成一系列动作，这种对单元的组装就是结构组件Chain。

如果需要在两个行为组件之间选择一个，则需要对Context进行判断，这种封装了判断的行为组件就是Converter的另外一个变种Validator。Validator对Context进行判断操作，返回的是一个Boolean类型的输出。有了Validator，即可以组合成BiBranch这种结构组件。

同理，有时我们需要基于Context在多个行为组件之间进行选择，这种封装了选择行为的组件就是Converter的另一个变种Locator。Locator对Context进行标识符的识别判断，返回的是一个String类型的输出。有了Locator，即可组合成Branch这种结构组件。

基于Validator和单个行为组件，我们还可以构建While Loop和Do While loop结构组件，完成前置或后置条件判断的循环操作。

如果希望复用某个已有的行为组件，但接口与我们需要的不一至则可以通过Adapter来做适配

如果希望对某个组件做修饰，可以使用Decorator组件。Decorator的行为自动与被修饰的组件保持一致。

# 组件
xunit的组件可以分为行为组件和结构组件。行为组件定义真的Context能做的处理；结构组件则对行为组件进行组合，将多个行为组件结合为更大，结构更复杂的行为组件。与行为组件不同的是，结构组件的行为模式需要手工指定，缺省是Processor。

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
这个是用户自创的方式，目前的编辑器依据缺省支持
主图和子图在不同文件，通过通用的dispatcher来实现结合

### 案例1
![uc1](https://github.com/hejiehui/xUnit/blob/master/doc/uc_user_ref.png)

### 案例2
![uc2](https://github.com/hejiehui/xUnit/blob/master/doc/uc_station_ref.png)
