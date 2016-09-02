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

# 组件
## 行为组件
### processor
对Context进行处理，但没有返回值
    public interface Processor extends Unit{
    	void process(Context ctx);
    }

### converter
对传入的context进行转换，转变为新的Context。也可以返回原来的Context
    public interface Converter extends Unit{
    	Context convert(Context inputCtx);
    }

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

### if-else
通过Validator决定调用内部那个unit

### branch
通过Locator判断调度内部那个unit

### while
通过Validator判断的while结构

### do while loop
通过Validator判断的do while结构

### decorator

### adapter

# 编辑方法
直接选择需要的组件，点击编辑器特定的区域

点击和对象组合 – E.g. Validator + Unit = if/else structure

# 配置
unit可配置
可以在应用或构建单元层次上面配置参数
