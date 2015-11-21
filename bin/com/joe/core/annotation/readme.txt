有关Annotation的继承说明：
1、JDK文档中的说明是：只有在类上应用的Annotation才能被继承，而实际应用时的结果是：除了类上应用的Annotation能被继承外，没有被重写的方法的Annotation也能被继承。
2、要注意的是：当方法被重写后，Annotation将不会被继承。
3、要使得Annotation 被继承，需要在Annotation中加标识@Inherited，并且如果要被反射应用的话，就需要还有个@Retention(RetentionPolicy.RUNTIME) 标识
4、Annotation的继承不能应用在接口上

说明：
com.joe.core.annotation.AuthResource：
	用于认证的注解，用户登录用的是此类注解

com.joe.core.annotation.I18n
    用于国际化，文本message
    
com.joe.core.annotation.Init
    用于初始化的注解，这个应用程序的初始化，初始化完成之后就不需要再进行初始化了。
    通过程序自己初始化
    在config.properties文件中，会指明几个初始化的配置，配置是以sky.inited.
   
com.joe.core.annotation.InitResource
	用于初始化的资源，如：数据库的初始化
	通过REST 进行初始化
	在config.properties文件中，会指明几个初始化的配置，配置是以sky.resource.inited.
	
com.joe.core.annotation.Plugin
	用于插件
	
com.joe.core.annotation.RestResource
	除了auth、init 之外其它REST资源的注解
    