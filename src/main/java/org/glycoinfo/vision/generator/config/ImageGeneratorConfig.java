package org.glycoinfo.vision.generator.config;

import org.eurocarbdb.application.glycanbuilder.BuilderWorkspace;
import org.eurocarbdb.application.glycanbuilder.renderutil.GlycanRenderer;
import org.eurocarbdb.application.glycanbuilder.renderutil.GlycanRendererAWT;
import org.eurocarbdb.resourcesdb.Config;
import org.eurocarbdb.resourcesdb.io.MonosaccharideConversion;
import org.eurocarbdb.resourcesdb.io.MonosaccharideConverter;
import org.glycoinfo.vision.generator.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageGeneratorConfig {
  
  @Bean
  BuilderWorkspace builderWorkspace(GlycanRenderer glycanRenderer) {
    return new BuilderWorkspace(glycanRenderer);
  }

//<bean id="glycanRenderer" class="org.eurocarbdb.application.glycanbuilder.GlycanRendererAWT"/>
  @Bean
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
  MonosaccharideConversion monosaccharideConverter(Config config) {
    return new MonosaccharideConverter(config);
  }

  @Bean
  Config config() {
    return new Config();
  }
  
  @Bean
  ImageGenerator imageGenerator() {
    return new ImageGenerator();
  }
}
