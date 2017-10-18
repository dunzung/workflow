基于Activiti5 定制化中国式工作流系统

##工作流开发手册_*V3.0*
	作者 段志军 2016-01-18
>###1.简介 

>###2.依赖关系

>> 2.1 软件环境

>> 2.2 工作流软件包

>> 2.3 数据库脚本

>###3.集成工作流

>> 3.1 模块说明

>> 3.2 工作流引擎配置

>> 3.3 工作流事务管理

>> 3.4 流程引擎的API和服务

>> 3.4 流程触发器

>> 3.5 流程设计器

>> 3.7 示例

>###4.工作流服务接口

>> 4.1 核心服务接口 

>>> 4.1.1 任务实例服务接口 

>>> 4.1.2 流程实例服务接口 

>>> 4.1.3 流程定义服务接口  

>>> 4.1.4 流程和任务参数服务接口 

>> 4.2 历史查询服务接口

>>> 4.2.1 历史流程实例服务接口

>>> 4.2.2 历史任务实例服务接口 

>> 4.3 扩展服务接口

>>> 4.3.1 触发器服务接口

>>> 4.3.2 流程参与人服务接口

>>> 4.3.3 流程代理人服务接口

>>> 4.3.4 历史任务扩展服务接口

>>> 4.3.5 任务定义扩展服务接口

>###5. 限制场景
>> 5.1 工作流V3.0

>###6. 交流及反馈

## 1. 简介	

工作流开发手册V3.0的使用者是开发者。

工作流V3.0底层是基于 [Activiti](http://activiti.org/userguide/index.html "官网开发文档") 进行开发完成的，整合了工作流V2.x的相关接口，易于开发者对现有工作流进行升级改造。 

**关于Activiti**

Activiti项目是一项新的基于Apache许可的开源BPM平台，从基础开始构建，旨在提供支持新的BPMN 2.0标准，包括支持对象管理组（OMG），面对新技术的机遇，诸如互操作性和云架构，提供技术实现。
创始人Tom Baeyens是JBoss jBPM的项目架构师，以及另一位架构师Joram Barrez，一起加入到创建Alfresco这项首次实现Apache开源许可的BPMN 2.0引擎开发中来。
Activiti是一种轻量级，可嵌入的BPM引擎。 它将提供宽松的Apache许可2.0，以便这个项目可以广泛被使用，同时促进Activiti BPM引擎和的BPMN 2.0的匹配，该项目现正由OMG通过标准审定。 加入Alfresco Activiti项目的是VMware的SpringSource分支，Alfresco的计划把该项目提交给Apache基础架构，希望吸引更多方面的BPM专家和促进BPM的创新。

> 1. [Activiti用户手册](http://activiti.org/userguide/index.html "Activiti用户手册")
> 2. [Activiti文档下载](http://activiti.org/download.html)

## 2. 依赖关系

### 2.1 软件环境
	
**JDK 6+**	

Activiti需要运行在JDK 6或以上版本上。 进入 [Oracle Java SE](http://www.oracle.com/technetwork/java/javase/downloads/index.html "Oracle下载页") 下载页面 点击 "下载 JDK"按钮。页面上也提供了安装的方法。
为了验证是否安装成功，可以在命令行中执行 java -version。 它将会打印出安装的JDK的版本。

**IDE** 

Eclipse、IntelliJ IDEA等

**IE** 

由于我司银行用户使用工作流较多，需提供对IE浏览器支持，除了流程设计器最低版本要求**IE9**，工作流其他模块兼容IE8、IE9、IE10。

**Spring**

工作流V3.0采用 [Spring](http://spring.io/ "Spring") 注解方式管理资源和Bean对象，与Spring无缝结合。开发者需在项目中集成工作流V3.0时，注意Spring版本。

**Maven**  

安装工作流相关开发包

**Activiti**

工作流V3.0是基于Activiti-5.18.0版本开发，所以依赖如下：

		<dependencies>

			<dependency>
				<groupId>org.activiti</groupId>
				<artifactId>activiti-engine</artifactId>
				<version>5.18.0</version>
			</dependency> 

			<dependency>
				<groupId>org.activiti</groupId>
				<artifactId>activiti-modeler</artifactId>
				<version>5.18.0</version>
			</dependency> 
			
			<dependency>
				<groupId>org.activiti</groupId>
				<artifactId>activiti-spring</artifactId>
				<version>5.18.0</version>
			</dependency> 

			<dependency>
				<groupId>org.activiti</groupId>
				<artifactId>activiti-explorer</artifactId>
				<version>5.18.0</version>
				<exclusions>
					<exclusion>
						<groupId>com.vaadin</groupId>
						<artifactId>vaadin</artifactId>
					</exclusion>
					<exclusion>
						<groupId>com.vaadin.addons</groupId>
						<artifactId>dchats-widget</artifactId>
					</exclusion>
					<exclusion>
						<groupId>com.vaadin.addons</groupId>
						<artifactId>activiti-simple-workflow</artifactId>
					</exclusion>
				</exclusions>
			</dependency> 
			...
		</dependencies>


### 2.2 工作流软件包

开发者在使用工作流软件包时，分为两种模式。 如下：

- **开发模式**
	
	开发者需要在项目POM文件中引入工作流依赖项

- **离线模式**
	
	由于客户现场环境限制，需要要手动拷贝工作流相关资源。如jar、css、js、数据库脚本、页面等等。 

> **一. 开发模式**

**首先，在POM中引用工作流jar**

1. 把app-workflow-core开发包放在对应的X-X-core工程POM文件中。

		<dependencies>
			<dependency>
				<groupId>com.vprisk.workflow</groupId>
				<artifactId>app-workflow-core</artifactId>
				<version>3.0.0</version>
			</dependency> 
			...
		</dependencies>

1. 把app-workflow-mvc放在X-X-mvc工程POM文件中。

		<dependencies> 
			 
			<dependency>
				<groupId>com.vprisk.workflow</groupId>
				<artifactId>app-workflow-mvc</artifactId>
				<version>3.0.0</version>
			</dependency>
			...
		</dependencies>

**其次，在POM中引用视图资源**

把app-workflow-web开发包放在对应的X-X-web工程POM文件中。

	<dependencies> 		 
		<dependency>
			<groupId>com.vprisk.workflow</groupId>
			<artifactId>app-workflow-web</artifactId>
			<version>3.0.0</version>
			<type>war</type>
		</dependency>
 
		<dependency>
			<groupId>com.vprisk.workflow</groupId>
			<artifactId>app-workflow-web</artifactId>
			<version>3.0.0</version>
			<type>warpath</type>
		</dependency>
		...
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<configuration>
					<packageExcludes>WEB-INF/web.xml</packageExcludes>
					<overlays>
						<overlay>
							<groupId>com.vprisk.workflow</groupId>
							<artifactId>app-workflow-web</artifactId>
							<includes>
								<!-- <include>ec/**</include> -->  <!-- 不引用该war的某些路径下文件 -->
								...
							</includes>
						</overlay>
						...
					</overlays>
				</configuration>
			</plugin>
		</plugins>
	</build>

**二. 离线模式： 由开发者直接拷贝工作流相关文件**

> **1. 拷贝Activiti jar文件**

![](http://i.imgur.com/blJJrYm.png)

> **2. 拷贝工作流 jar文件**

- app-workflow-core 
 
	MVC模式下的工作流服务层，负责工作流流程定义、任务审批、历史查询以及参与人等。

- app-workflow-mvc 
 
	MVC模式下的工作流控制层

关于MVC模式理解，参考[MVC](http://baike.baidu.com/link?url=G6yvG7b8ZhUOrrJytKGYeU1wmYj3U-NA2JnufQuycdlNo0VipFY3j-Ap6KS0Ph0U-lA1gf_NgzXCIWghGNME1ftxbgpcEw-dnJ9Qrf8khG-N3U1dmV350cIdVFhPkbMLqsA0LNyRnznqI8e7_hGmN7YSuQdRJ5Nl6ig9pNyJBLG)

> **3. 拷贝工作流引擎配置类**

在app-workflow-web下：

	com.vprisk.config.WorkflowApplicationConfig

> **4. 拷贝工作流JSP文件**

![](http://i.imgur.com/Yb4v0LD.png)

> **5. 拷贝流程设计器文件**

在app-workflow-web工程的/workflow目录下，其中**stencilset.json**是流程设计器组件配置库。

![](http://i.imgur.com/Ygt2Ci4.png)

在app-workflow-web/webapp/flowdesigner目录下是流程设计器的页面、css、javascripts、图片。

![](http://i.imgur.com/bpJfqw2.png)

### 2.3 数据库脚本

> **1. 数据库**

目前工作流V3.0支持Oracle和DB2

> **2. 理解数据库表的命名**

工作流V3.0的表都以ACT_开头。 第二部分是表示表的用途的两个字母标识。 

	ACT_ID_*: 'ID'表示identity。 这些表包含身份信息，比如用户，组等等。

	ACT_HI_*: 'HI'表示history。 这些表包含历史数据，比如历史流程实例，参数，任务等等。

	ACT_GE_*: 通用数据， 用于不同场景下。

	ACT_RE_*: 'RE'表示repository。 这个前缀的表包含了流程定义和流程静态资源（图片，规则，等等）。

	ACT_RU_*: 'RU'表示runtime。 这些运行时的表，包含流程实例，任务，参数，异步任务，等运行中的数据。
			  Activiti只在流程实例执行过程中保存这些数据， 在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。

	ACT_EX_*: 'EX'表示extension。 这个前缀的表包含了代理人设置、触发器、审批历史、流程和任务定义扩展属性等。

> **3. 数据库脚本**

**分为两部分**

- 	一、工作流引擎表
- 	二、工作流扩展表

**工作流引擎表**在工作流V3.0软件开发包中。在系统初始化时，工作流引擎自动初始化数据库脚本，不需要人为干预。

工作流引擎脚本在activiti-engine-5.18.0.jar中：

![](http://i.imgur.com/irXeONf.png)

而**工作流扩展表**是针对工作流引擎表做的一些定制化配置的表，需要开发者手动整合到业务系统。

> **4. 扩展表数据库脚本**

扩展的数据库脚本文件存放在工作流app-workflow-web工程的db/mini目录下：

![](http://i.imgur.com/CZi8vEH.png)

在实际的业务系统开发过程中，根据使用的数据库类型，开发者可以直接把db/mini/db2或者db/mini/oracle目录下的workflow-table.sql和workflow-data.sql文件拷贝到业务系统初始化脚本目录下。

**创建表**
	
	workflow-table.sql

**扩展表说明**
 
	代理表 ACT_EX_AGENT 
	代理流程列表 ACT_EX_AGENT_SCOPE 
	流程任务历史扩展表 ACT_EX_HISTORY 
	流程和任务触发器扩展表 ACT_EX_LISTENER 
	流程和任务定义扩展表 ACT_EX_NODE_VARIABLE 

在创建DB2表时，需要指定表空间，如：

	CREATE TABLE TEST (
		COL1 VARCHAR(20) NOT NULL,
		...
	)
	in 指定表空间名
	index in  指定表空间名

 **初始化数据**
	
    workflow-data.sql 

包括字典参数和流程菜单两部分

1、 字典参数
	 
![](http://i.imgur.com/oqkn6Zc.png)
![](http://i.imgur.com/kWukgnS.png)
	 
2、 流程菜单

![](http://i.imgur.com/OAfzmOu.png)

## 3. 集成工作流

### 3.1 模块说明

- **app-workflow-core** 

	工作流核心服务接口，负责流程定义更新与查询、流程实例更新与查询、任务审批与查询，参与人查询、流程代理更新与查询、流程历史查询等。

	![](http://i.imgur.com/gs2lIPC.png)

- **app-workflow-mvc** 

	负责工作流数据传递，页面地址跳转

	![](http://i.imgur.com/PcAjMQT.png)

- **app-workflow-web** 
 
	负责数据库配置，事务管理、数据展示以及页面展示。

	![](http://i.imgur.com/gs2rF2U.png)

### 3.2 工作流引擎配置

工作流引擎采用Spring注解配置类，管理工作流引擎数据源-dataSource配置、数据库事务-transactionManager：

	com.vprisk.config.WorkflowApplicationConfig

![工作流引擎配置](http://i.imgur.com/ixfzFps.png)

以及配置Activiti审批服务接口，如流程定义以及流程相关静态资源RepositoryService：
![](http://i.imgur.com/if7Fesf.png)

**流程实例RuntimeService**

![](http://i.imgur.com/kdHoPuA.png)

**任务服务TaskService**

![](http://i.imgur.com/JoB44u4.png)

**历史服务HistoryService**

![](http://i.imgur.com/AigdBtF.png)

等服务接口，更多详细信息请参考[Activiti用户手册](http://activiti.org/userguide/index.html "Activiti用户手册")。

### 3.3 工作流事务管理

工作流V3.0通过Spring的注解方式管理事务，一般事务控制在Service服务类的方法级别上，如保存流程定义:

	@Transactional
	public void saveProcessDefinition(ProcessDefinitionDto dto){
		...
	}

通过@Transactional来标识需要事务，这时候与Spring事务管理结合起来呢。

### 3.4 流程引擎的API和服务

![](http://i.imgur.com/Yz9wkrP.png)

> **1. 流程定义服务接口**

流程定义是工作流V3.0核心功能之一，流程定义服务提供了管理和控制流程定义的操作。 包括新增、编辑、复制、锁定、激活、导入、导出、删除。它包含了一个流程每个环节的结构和行为。 

> **2. 流程实例服务接口**

流程实例服务是工作流V3.0核心功能之一。 包括启动流程，挂起、激活、删除、复位发起、查询、流程进度图等。

流程实例服务负责启动一个流程定义的新实例。 流程定义定义了流程各个节点的结构和行为。 流程实例就是这样一个流程定义的实例。对每个流程定义来说，同一时间会有很多实例在执行。 流程实例服务可以用来获取和保存流程参数。 这些数据是特定于某个流程实例的，并会被很多流程中的节点使用 （比如，一个决策器常常使用流程参数来决定流转到指定分支走流程）。 流程实例服务也能查询流程实例和执行。 最后，流程实例服务可以在流程实例等待外部触发时使用，这时可以用来继续流程实例。 流程实例可以有暂停状态，而服务提供了激活方法来“激活”实例， 接受外部激活后，流程实例就会继续向下执行。

**流程进度图**
![](http://i.imgur.com/V6xWE9Y.png)
其中标红的箭头是已走完的，标红任务节点是当前要审批节点。

> **3. 任务实例接口**

任务实例是由系统中真实人员执行的，它是工作流V3.0核心功能之一。包括任务审批，查询用户任务、任务改派，任务驳回等。
 
> **4. 历史查询服务接口**

流程历史是一个组件，它可以捕获发生在进程执行中的信息并永久的保存，与运行时数据不同的是，当流程实例运行完成之后它还会存在于数据库中。

历史查询服务提供了工作流的所有历史数据。 在执行流程时，引擎会保存很多数据，比如流程实例启动时间，任务的参与者， 完成任务的时间，每个流程实例的执行路径等等。 这个服务主要通过查询功能来获得这些数据。

> **5. 触发器服务接口**

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询；

触发器可以理解为流程审批过程中，中间嵌入的一段小程序，他的主要用途在于可以做一些审批任务之外的一些事情，如操作业务数据，分配下一节点审批人、记录日志，消息提醒等等。

- 在任务审批前或审批完成后，开发者可自定义前置触发器或者后置触发器，
- 在决策器走流转分支时，指定流转分支路由

触发器包括前置触发器和后置触发器

**触发器分类**
	
- 任务触发器
- 决策器触发器

> **7. 流程参与人服务接口**

工作流V3.0扩展服务接口之一，流程参与人服务可以查询流程审批相关审批人，机构、角色。

> **8. 历史任务扩展服务接口**

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询；

在任务审批前或审批完成后，开发者可自定义前置任务触发器或者后置任务触发器，在触发器中可记录一些与业务相关的日志信息或者审批任务消息提醒等。

> **9. 流程代理服务接口**

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询；

有些时候有的领导在一段时间内有事不能审批，比如出差。那问题来了，如果领导不能审批任务，整个流程将会停滞不前。所以这个时候需要有人代领导审批任务，流程代理服务就派上用场了。流程代理服务允许领导指定代理人在一段之间内，代理指定流程，审批任务。

> **10. 任务定义扩展服务接口**

工作流V3.0扩展服务接口之一，流程设计器扩展了流程定义属性，而这些属性不属于工作流引擎本身，需要额外用数据库表存储。主要用途在于流程审批时，可以把流程定义属性(参数)当作流程审批权重，影响任务完成。比如回退规则、审批人、是否会签、允许改派等。

### 3.4 流程触发器

> **1. 触发器触发条件类型(type)**
	
**任务级别**

- create 任务实例创建前触发
- complete 任务实例完成后触发

**流程级别**

- start 节点实例创建前触发
- end 节点实例完成后触发

>**2. 自定义任务触发器**

开发者自定义任务触发器需要实现任务级触发器接口，如：

	public class WorkflowTaskListenerA implements WorkflowTaskListener{
			
		public void notify(DelegateTask delegateTask){
			//....
		}
	}

开发者自定义决策器触发器需要实现流程级触发器接口，如：

>**3. 自定义决策器触发器**

	public class WorkflowTaskListenerB implements WorkflowTaskListener{
			
		public void notify(DelegateExecution execution){
			//....
		}
	}

>**4. 自定义触发器安装**

开发者需编写SQL插入脚本进行手动安装，如：

![](http://i.imgur.com/lbOXnqS.png)
![](http://i.imgur.com/MK5iCJ1.png)

然后在新建流程定义页面的属性栏显示，如：

![](http://i.imgur.com/Z9FBQqO.png)

### 3.5 流程设计器

在app-workflow-web工程目录下

![](http://i.imgur.com/Ygt2Ci4.png)

其中**stencilset.json**是流程设计器组件配置库。

![](http://i.imgur.com/Cdzg6k2.png)
webapp/flowdesigner包含网页版流程设计器页面、css、script等资源。

**网页版流程设计器**

网页版流程设计器主要由[Angularjs](http://docs.angularjs.cn/api "angularjs")，[SVG矢量图](http://baike.baidu.com/link?url=AIuYbJR_ilZ5BgdHjBVRjSy96mFIfCoTRVfmIsccU3-qFogvXFeIlK293C-uPvjm1r8zs0FDieZMXMS2VWL7hsB1jHFwK9y-uLvUhdZa77a "SVG") 开发而成。对IE最低版本要求是**IE9**，请开发者注意。

![](http://i.imgur.com/MuJh7jn.png)

### 3.6 示例

假设已经把工作流集成完毕并运行起来，演示公司简单的请假申请流程：

> **保存请假申请流程定义**

在流程设计器定义一条流程编号为VC_1001的请假申请流程
 ![](http://i.imgur.com/RusG0so.png)
            
为了让工作流引擎知道这个流程，我们必须先进行 >创建>激活>发布。 发布意味着引擎会把流程定义解析成可以执行的流程定义文件， 并且该流程定义会被添加到数据库中。 这样，当引擎重启时，该流程定义依然存在。代码如下：

	@Autowired
	private ProcessDefinitionService processDefinitionService

	ProcessDefinitionDto dto = ...;
	processDefinitionService.saveProcessDefinition(dto)

> **挂起，激活一条流程定义**

我们可以挂起一条流程定义。当挂起流程定义时，就不能创建新流程实例了。

	String processDefinitionKey = "VC_1001";
	processDefinitionService.suspendProcessDefinition(processDefinitionKey);
            
要想重新激活一条流程定义，可以调用：

	String processDefinitionKey = "VC_1001";
	processDefinitionService.activateProcessDefinition(processDefinitionKey)
            
> **启动一条流程实例**

把流程定义发布到工作流引擎后，我们可以基于它发起新流程实例。 对每个流程定义，都可以有很多流程实例。 流程定义是“蓝图”，流程实例是它的一个运行的执行实例。

所有与流程运行状态相关的数据都可以通过ProcesInstanceService获得。 有很多方法可以启动一个新流程实例。在下面的代码中，我们使用定义在流程定义文件中的流程编号来启动流程实例。 我们也可以在流程实例启动时添加一些流程参数，因为第一个用户任务的表达式需要这些参数。 流程参数经常会被用到，因为它们赋予来自同一个流程定义的不同流程实例的特别含义。 简单来说，流程参数是区分流程实例的关键。代码如下：

	@Autowired
	private ProcessInstanceService processInstanceService

	Map<String, Object> variables = new HashMap<String, Object>();
	variables.put("employeeName", "duanzhijund");
	variables.put("numberOfDays", new Integer(4));
	variables.put("descr", "请假四天!");
	
	String processDefinitionKey = "VC_1001"
	ProcessInstanceDto dto = processInstanceService.startProcessInstanceByLatestVersionKey(processDefinitionKey, variables);

> **查询审批任务**
	
流程启动后，第一步就是用户任务。这是必须由系统用户处理的一个环节。 通常，用户会有一个“任务列表”，展示了所有必须由该用户处理的任务。 下面的代码展示了对应的分页查询：

	@Autowired
	private TaskInstanceService taskInstanceService

	// 分页查询
	Pageable pageable = ...;
	Map<String, Object> params = ...;
	params.put("processName", "")	
	params.put("taskName", "")	
	params.put("createdTmBegin", "")
	params.put("createdTmEnd", "")

	Page<TaskInstanceDto> = taskInstanceService.pageTaskPending(pageable, params) 
	for (Task task : tasks) {
	  Log.info("Task available: " + task.getName());
	}

> **完成任务**

为了让流程实例继续运行，我们需要完成整个任务。下面的代码展示了如何做这件事：

	@Autowired
	private TaskInstanceService taskInstanceService

	Map<String, Object> taskVariables = new HashMap<String, Object>();
	taskVariables.put("resultFlag", "Y"); // or "N"
	taskVariables.put("comment", "审批意见!");
	...

	Task task = tasks.get(0);
	String nextTaskCode  = ...; // 下一任务节点编号
	taskInstanceService.pass(task.getId(), nextTaskCode , taskVariables);
            
流程实例会进入到下一个环节。在这里例子中， 下一环节允许员工通过表单调整原始的请假申请。员工可以重新提交请假申请， 这会使流程重新进入到第一个任务。

> **挂起，激活一个流程实例**

也可以挂起一条流程实例。挂起时，流程审批不能继续执行。

	String processInstanceId = "";
	processInstanceService.suspendProcessInstance(processInstanceId);

激活流程实例可以调用：

	String processInstanceId = "";
	processInstanceService.activateProcessInstance(processInstanceId);

更多详细服务接口可以参考**工作流服务接口**。

## 4. 工作流服务接口

### 4.1 核心服务接口

#### 4.1.1 任务实例服务接口
	
com.vprisk.workflow.core.TaskInstanceService
	
**引用**

	@Autowired
	public TaskInstanceService taskInstanceService

**4.1.1.1 任务详细查询**  

> 根据任务实例UUID查询任务实例。

	String taskId = ...; // 任务实例UUID
    TaskInstanceDto task = taskInstanceService.find(String taskId)

**4.1.1.2 待办任务分页查询**

> 待办任务分页。

	Pageable pageable = ...;
	Map<String, Object> params = ...;
	params.put("processName", "")	
	params.put("taskName", "")	
	params.put("createdTmBegin", "")
	params.put("createdTmEnd", "")

	public Page<TaskInstanceDto> = taskInstanceService.pageTaskPending(pageable, params) 
    
**4.1.1.3 查询任务候选人**

> 根据流程定义UUID、任务Code以及流程实例UUID，查询任务审批候选人。

	String procDefId = ...; // 流程定义UUID
	String nextTaskCode  = ...; // 下一个任务定义Code
	String procInstId = ...; // 流程实例UUID
	String candidates = taskInstanceService.findCandidatesByRollbackRule(procDefId, nextTaskCode , procInstId)
 	
**4.1.1.4 根据流程实例id查询待办任务列表**

> 根据流程实例UUID，查询待办任务列表。

	String procInstId = ...;
	List<TaskInstanceDto> list = taskInstanceService.findTaskByProcessInstanceId(procInstId)

**4.1.1.5 是否改派**

> 根据任务实例UUID，判断审批任务是否可以改派。

	String taskId = ...;
	boolean flag = taskInstanceService.isDelegate(taskId) 

**4.1.1.6 任务改派**

> 根据任务实例UUID以及改派人，完成改派。 改派后，当前任务属于改派指定的assignee。只有指定的assignee能再次看到该审批任务。

@Transactional

	String taskId = ...;
	String assignee = ...;
	taskInstanceService.delegateTask(taskId, assignee) 

**4.1.1.7 （批量）任务审批**

> 根据任务实例UUID列表，任务定义Code以及任务参数批量完成任务。只有流程相同，审批节点相同才能进行批量审批

@Transactional

	String [] taskIds = ...;
	String nextTaskCode = ...;
	Map<String, Object> variables = ...;
    taskInstanceService.pass(taskIds, nextTaskCode, variables) 
	
**4.1.1.8 会签任务审批**

> 同上

@Transactional

	String [] taskIds = ...;
	String nextTaskCode = ...;
	Map<String, Object> variables = ...;
    taskInstanceService.assign(taskIds, nextTaskCode, variables);

**4.1.1.9 批量）批量任务审批驳回**

> 根据任务实例UUID列表，任务定义Code以及任务参数批量驳回任务。只有流程相同，审批节点相同才能进行批量审批驳回

@Transactional

	String [] taskIds = ...;
	String nextTaskCode = ...;
	Map<String, Object> variables = ...;
    taskInstanceService.rollbackTask(taskIds, nextTaskCode, variables);


**4.1.1.10 保存流程参数**

> 根据任务实例，任务定义Code以及任务参数保存流程参数。

@Transactional

	Task task = ...;
	String nextTaskCode = ...;
	Map<String, Object> variables = ...;
    taskInstanceService.saveTaskVariable(task, nextTaskCode, variables);

该保存方法有事务控制。

**4.1.1.11 保存任务私有参数**

> 根据任务实例UUID以及任务参数保存任务私有参数。

@Transactional

	String taskId = ...;
	Map<String, Object> variables = ...;
    taskInstanceService.saveTaskVariableLocal(taskId, variables);

----------

### 4.1.2 流程实例接口

com.vprisk.workflow.core.ProcessInstanceService 

**引用**

	@Autowired
	public ProcessInstanceService processInstanceService;

**4.1.2.1 流程复位发起**
	
> 根据已完成的流程实例UUID来发起新的流程，新老流程实例建立绑定关系。

@Transactional

	String procInstId = ...;
    ProcessInstanceDto p = processInstanceService.restartProcessInstance(procInstId);

**4.1.2.2 流程启动**

> 根据流程定义UUID以及流程参数启动流程实例。

@Transactional

	String processDefinitionId = ...;
	Map<String, Object> variables = ...;
	variables.put("applyUser", "") // 流程发起人
	variables.put("bizUrl", "") // 业务表单地址
	variables.put("process_biz_key", "") // 业务主键UUID

    ProcessInstanceDto p = processInstanceService.startProcessInstanceById(processDefinitionId, variables) 

**4.1.2.3 流程启动**

> 根据流程定义编号、版本以及流程参数启动流程实例。

@Transactional
	
	String processDefinitionKey = ...;
	String processDefinitionVersion = ...;
	Map<String, Object> variables = ...;
	variables.put("applyUser", "") // 流程发起人
	variables.put("bizUrl", "") // 业务表单地址
	variables.put("process_biz_key", "") // 业务主键UUID

    ProcessInstanceDto p = processInstanceService.startProcessInstanceByKeyAndVersion(processDefinitionKey, processDefinitionVersion，variables) 
 

**4.1.2.4 流程启动**

> 根据最新的流程定义版本、编号、流程参数启动流程实例。

@Transactional

	String processDefinitionKey = ...;
	Map<String, Object> variables = ...;
	variables.put("applyUser", "") // 流程发起人
	variables.put("bizUrl", "") // 业务表单地址
	variables.put("process_biz_key", "") // 业务主键UUID

    ProcessInstanceDto p = processInstanceService.startProcessInstanceByLatestVersionKey(processDefinitionKey, variables) 

**4.1.2.5 流程实例详细**

> 根据流程实例UUID查询流程实例对象。

	String procInstId = ...;
    ProcessInstanceDto p = processInstanceService.findProcessInstanceById(procInstId) 

** 4.1.2.5 流程实例进度图**

> 根据流程实例UUID生成流程进度图。

	String procInstId = ...;
    InputStream in = processInstanceService.generateTrace(procInstId) 

**4.1.2.5 流程实例挂起**

> 根据流程实例UUID挂起流程实例，挂起的流程实例不能进行任务审批。

@Transactional

	String procInstId = ...;
    processInstanceService.suspendProcessInstance(procInstId) 

**4.1.2.6 流程实例激活**

> 根据流程实例UUID激活流程实例，只有运行中的流程实例才能进行任务审批。

@Transactional
	
	String procInstId = ...;
    processInstanceService.activateProcessInstance(procInstId) 

**4.1.2.6 流程实例终止**

> 根据流程实例UUID强制终止流程实例，流程实例运行强制结束，状态变为已终止。

@Transactional

	String procInstId = ...;
    processInstanceService.terminateProcessInstance(procInstId) 
 
**4.1.2.6 流程实例删除**

> 根据流程实例UUID删除流程实例。

@Transactional
	
	String procInstId = ...;
    processInstanceService.deleteProcessInstance(procInstId) 

----------

### 4.1.3 流程定义接口  
	
com.vprisk.workflow.core.ProcessDefinitionService 

**引用**

	@Autowired
	public ProcessDefinitionService processDefinitionService;

**4.1.3.1 流程定义分页查询**

> 分页查询。

	Pageable pageable = ...;
	Map<String, Object> params = ...;
	params.put("defineName", "")	
	params.put("defineCode", "")	
	params.put("status", "")

    Page<ProcessDefinitionDto> page = processDefinitionService.pageProcessDefinition(pageable,  params) 

**4.1.3.2 流程定义列表查询**

> 根据流程定义参数查询流程定义列表。

	Map<String, Object> params = ...;
	params.put("defineName", "")	
	params.put("defineCode", "")	
	params.put("status", "")

    List<ProcessDefinitionDto> list = processDefinitionService.selectProcessDefinition(params);

**4.1.3.3 查询流程定义最新版本**

> 根据流程定义编号最新流程定义。

	String processDefinitionKey = ...;
    ProcessDefinitionDto p = processDefinitionService.findLatestProcessDefinitionByCode(processDefinitionKey) ;

**4.1.3.4 查询未审批节点**

> 根据任务实例UUID查询未审批的任务节点。

	String taskId = ...;
    List<ActivityDto> list = processDefinitionService.findUnApprovedActivitiesByTaskId(taskId); 

**4.1.3.5 查询已审批过的节点**

> 根据任务实例UUID查询已审批过的任务节点。

	String taskId = ...;
    List<ActivityDto> list = processDefinitionService.findApprovedPreviousActivitiesByTaskId(taskId); 

**4.1.3.6 批量导入流程定义**

> 从页面导入“workflow.zip”，完成流程定义批量导入。适合于系统间的流程定义数据迁移。zip包含后缀为.json的流程定义文件。
一个json文件对应一条流程定义。

@Transactional

	String zipName = ...;
	InputStream inputStream = ...;
    processDefinitionService.impProcessDefinitionZip(inputStream, zipName)

**4.1.3.6 批量导出流程定义**

> 批量导出流程定义后会生成ZIP包“workflow.zip”，供页面下载。 ZIP包含后缀为.json的流程定义文件，一个json文件对应一条流程定义。

@Transactional

	OutputStream outputStream = ...;
	Map<String, Object> params = ...;
    ZipOutputStream out = processDefinitionService.expProcessDefinitionZip(params, outputStream);

**4.1.3.7 判断流程定义编号是否存在**

> 判断流程定义编号是否存在

@Transactional

	String processDefinitionKey = ...;
    boolean flag = processDefinitionService.existDefinitionKey(processDefinitionKey);

**4.1.3.7 删除流程定义**

> 可以批量删除流程定义，如果其中的某条流程存在运行的流程实例，则会提示删除失败的错误信息。

@Transactional

	String [] processDefinitionIds = ...;
    processDefinitionService.deleteProcessDefinition(processDefinitionIds);

**4.1.3.7 锁定流程定义**

> 当需要挂起流程定义时，就不能创建新流程实例了。

@Transactional

	String processDefinitionId = ...;
    processDefinitionService.suspendProcessDefinition(processDefinitionId);
 
**4.1.3.7 激活流程定义**

> 当有挂起流程定义时，工作流提供激活流程定义功能。

@Transactional

	String processDefinitionId = ...;
    processDefinitionService.activateProcessDefinition(processDefinitionId);

**4.1.3.7 保存流程定义**

> 保存一条流程定义。

@Transactional

	ProcessDefinitionDto processDefinitionDto = ...;
    processDefinitionService.saveProcessDefinition(processDefinitionDto);
 
**4.1.3.7 复制流程定义**

> 复制类似的流程定义。

![](http://i.imgur.com/t70SXbn.png)

@Transactional

	ProcessDefinitionDto processDefinitionDto = ...;
    processDefinitionService.copyProcessDefinition(processDefinitionDto);

##### 4.1.4 流程参数接口

	com.vprisk.workflow.core.VariableService


> **流程参数包括**

1. 流程实例运行和历史参数
2. 任务实例运行和历史参数

历史任务实例服务接口、任务实例服务接口、流程实例接口都继承了流程参数接口，便于查询流程和任务参数。

**查询流程实例或任务实例共有参数列表**

    public Map<String,Object> getVariables(String tartgetId) 

**查询流程实例或任务实例私有参数列表**

    public Map<String,Object> getVariablesLocal(String tartgetId) 

**设置流程实例或任务实例共有参数**

@Transactional

    public Object setVariable(String tartgetId，String variableName) 

**设置流程实例或任务实例私有参数**

@Transactional

    public Object setVariableLocal(String tartgetId，String variableName) 

**删除流程实例或任务实例私有参数**

@Transactional

    public Object removeVariableLocal(String tartgetId，String variableName) 

**删除流程实例或任务实例共有参数**

@Transactional

    public Object removeVariable(String tartgetId，String variableName) 

##4.2 历史查询服务接口

历史查询服务提供了工作流的所有历史数据。 在执行流程时，引擎会保存很多数据，比如流程实例启动时间，任务的参与者， 完成任务的时间，每个流程实例的执行路径等等。 这个服务主要通过查询功能来获得这些数据。

###4.2.1 历史任务实例服务接口

com.vprisk.workflow.core.HistoricTaskInstanceService

工作流V3.0历史服务接口之一，查询与任务相关的历史数据。

**引用**

	@Autowired
	public HistoricTaskInstanceService historicTaskInstanceService; 

**4.2.1.1 查询指定流程已审批任务**

	Strubg processInstanceId = ...;
    List<WorkflowTableDto> list = historicTaskInstanceService.findListByProcessInstanceId(processInstanceId);

**4.2.1.2 分页查询审批任务**

	Pageable pageable = ...;
	Map<String, Object> params = ...;
	params.put("processName", "")	
	params.put("applyUser", "")	
	params.put("taskName", "")

    Page<WorkflowTableDto> page = historicTaskInstanceService.pageTaskApproved(pageable，params);

**4.2.1.1 查询流程参数**

	Strubg processInstanceId = ...;
    Map<String, Object> map = historicTaskInstanceService.getVariablesByProcessInstanceId(processInstanceId);
	
###4.2.2 历史流程实例服务接口

com.vprisk.workflow.core.HistoricProcessInstanceService 

工作流V3.0历史服务接口之一，查询与流程实例相关的历史数据。

**引用**

	@Autowired
	public HistoricProcessInstanceService historicProcessInstanceService;

**4.2.2.1 查询指定流程实例详细**

	Strubg processInstanceId = ...;
    ProcessInstanceDto dto = historicProcessInstanceService.findProcessInstanceById(processInstanceId);

**4.2.2.1 分页查询流程实例**

	Pageable pageable = ...;
	Map<String, Object> params = ...;
	params.put("processName", "")	
	params.put("applyUser", "")	
	params.put("status", "")

    Page<WorkflowTableDto> page = historicProcessInstanceService.pageProcess(pageable，params);

## 4.3 扩展服务接口

###4.3.1 触发器服务接口 ###

com.vprisk.workflow.core.ListenerService

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询。

**引用**

	@Autowired
	public ListenerService listenerService;

###4.3.2 流程参与人服务接口 ###

com.vprisk.workflow.core.ParticipantService

**引用**

	@Autowired
	public ParticipantService participantService;  
	
###4.3.3 历史任务扩展服务接口 ###

com.vprisk.workflow.core.TaskHistoryService 

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询。

**引用**

	@Autowired
	public TaskHistoryService taskHistoryService;

###4.3.4 流程代理服务接口 ###

com.vprisk.workflow.core.ProcessAgentService  

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询。
 

**引用**

	@Autowired
	public ProcessAgentService processAgentService;

###4.3.5 任务定义扩展服务接口 ###
 
com.vprisk.workflow.core.NodeVariableService  

工作流V3.0扩展服务接口之一。 基本功能包括新增，编辑、删除、查询。

**引用**

	@Autowired
	public NodeVariableService nodeVariableService; 


## 5.限制场景

### 5.1 工作流V3.0

1. 	流程定义不支持子流程
1. 	流程定义第二个节点不支持会签
1. 	任务审批不支持数据粒度权限
2. 	任务不支持撤回
3. 	不支持业务表单，类似于form，需要与实际的系统相结合，目前不在工作流考虑范围之内
4. 	决策器需要自定义触发器，完成分支流转
	

## 6.交流及反馈

如有疑惑，可以邮件交流，**zhijund@163.com**。 ：)
