package org.glycoinfo.vision.generator.config;

import org.eurocarbdb.application.glycanbuilder.BuilderWorkspace;
import org.eurocarbdb.application.glycanbuilder.renderutil.GlycanRenderer;
import org.eurocarbdb.application.glycanbuilder.renderutil.GlycanRendererAWT;
import org.eurocarbdb.resourcesdb.Config;
import org.eurocarbdb.resourcesdb.io.MonosaccharideConversion;
import org.eurocarbdb.resourcesdb.io.MonosaccharideConverter;
import org.glycoinfo.vision.generator.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ImageGeneratorConfig {
  
  @Bean
//  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  BuilderWorkspace builderWorkspace(GlycanRenderer glycanRenderer) {
    return new BuilderWorkspace(glycanRenderer);
  }

//<bean id="glycanRenderer" class="org.eurocarbdb.application.glycanbuilder.GlycanRendererAWT"/>
  @Bean
//  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  GlycanRenderer glycanRenderer() {
    return new GlycanRendererAWT(); 
  }
  
//  <bean id="monosaccharideConverter" class="org.eurocarbdb.resourcesdb.io.MonosaccharideConverter">
//  <constructor-arg>
//    <ref bean="config"/>
//  </constructor-arg>
//</bean>
//    <bean id="config" class="org.eurocarbdb.resourcesdb.Config"/>
  @Bean
//  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  MonosaccharideConversion monosaccharideConverter(Config config) {
    return new MonosaccharideConverter(config);
  }

  @Bean
  Config config() {
    return new Config();
  }
  
  @Bean
//  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  ImageGenerator imageGenerator() {
    return new ImageGenerator();
  }
}
