package nz.co.xingsoft.memribox.server.application;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.io.FilenameUtils;
import org.dozer.DozerBeanMapper;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("nz.co.xingsoft")
@ImportResource("classpath:/META-INF/spring/spring-root-context.xml")
public class SpringApplicationContext {

    @Bean
    public static PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        final Resource[] resourceLocations = properties();
        p.setLocations(resourceLocations);
        p.setIgnoreResourceNotFound(false);
        p.setIgnoreUnresolvablePlaceholders(false);
        return p;
    }

    static Resource[] properties() {
        final File file = new File("/opt/apps/memribox/conf/config.properties");
        if (file.canRead()) {
            return new Resource[] { new FileSystemResource(file) };
        }

        return new Resource[] { new ClassPathResource("config.properties") };
    }

    /**
     * Dozer bean
     * 
     * @return
     */
    @Bean
    public static DozerBeanMapper dozerBeanMapper() {
        final DozerBeanMapper mapper = new DozerBeanMapper();

        try {
            final File mappingFolder = new File(SpringApplicationContext.class.getClassLoader().getResource("META-INF/dozer").toURI());

            if (mappingFolder.canRead()) {
                final File[] mappingFiles = mappingFolder.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(final File file) {
                        return "xml".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()));
                    }
                });

                if (mappingFiles != null && mappingFiles.length > 0) {
                    final List<String> mappingFileList = new ArrayList<>();

                    for (final File mappingFile : mappingFiles) {
                        mappingFileList.add(mappingFile.toURI().toURL().toExternalForm());
                    }

                    mapper.setMappingFiles(mappingFileList);
                }
            }

        } catch (final Exception e) {

        }

        return mapper;
    }

    @Bean
    public static Validator hibernateValidator() {
        final HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();

        final ValidatorFactory factory = configuration.failFast(false).buildValidatorFactory();

        return factory.getValidator();

    }
}
