package com.jund.config;

import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WorkflowConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AnnotationSessionFactoryBean annotationSessionFactoryBean;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
        processEngine.setProcessEngineConfiguration(processEngineConfiguration());
        return processEngine;
    }

    @Bean
    public RepositoryService repositoryService() throws Exception {
        return processEngine().getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() throws Exception {
        return processEngine().getObject().getRuntimeService();
    }

    @Bean
    public TaskService taskService() throws Exception {
        return processEngine().getObject().getTaskService();
    }

    @Bean
    public HistoryService historyService() throws Exception {
        return processEngine().getObject().getHistoryService();
    }

    @Bean
    public ManagementService managementService() throws Exception {
        return processEngine().getObject().getManagementService();
    }

    @Bean
    public FormService formService() throws Exception {
        return processEngine().getObject().getFormService();
    }

    @Bean
    public IdentityService identityService() throws Exception {
        return processEngine().getObject().getIdentityService();
    }

    /**
     * <property name="activityFontName" value="宋体" />
     * <property name="labelFontName" value="宋体" />
     * <property name="dataSource" ref="dataSource" />
     * <property name="transactionManager" ref="transactionManager" />
     * <property name="databaseSchemaUpdate" value="true" />
     * <property name="jobExecutorActivate" value="false" />
     * <property name="typedEventListeners">
     * <map>
     * <entry key="PROCESS_STARTED,PROCESS_COMPLETED,PROCESS_COMPLETED_WITH_ERROR_END_EVENT,PROCESS_CANCELLED">
     * <list>
     * <ref bean="processStatusListener" />
     * </list>
     * </entry>
     * </map>
     * </property>
     *
     * @return
     */
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(transactionManager);
        configuration.setDatabaseSchemaUpdate("true");
        configuration.setDatabaseSchema("APP_WORFLOW");
        configuration.setJobExecutorActivate(false);
        configuration.setTypedEventListeners(typedListeners());
        return configuration;
    }

    private Map<String, List<ActivitiEventListener>> typedListeners() {
        Map<String, List<ActivitiEventListener>> typedListeners = new HashMap<String, List<ActivitiEventListener>>();
        List<ActivitiEventListener> listeners = new ArrayList<ActivitiEventListener>();
      //  listeners.add(processStatusListener);
        typedListeners.put("PROCESS_STARTED,PROCESS_COMPLETED,PROCESS_COMPLETED_WITH_ERROR_END_EVENT,PROCESS_CANCELLED", listeners);
        return typedListeners;
    }
}
