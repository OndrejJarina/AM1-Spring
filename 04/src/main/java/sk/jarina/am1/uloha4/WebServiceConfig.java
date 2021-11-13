package sk.jarina.am1.uloha4;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    //nastavenie uri, namespacu a wsdl definicie
    @Bean(name = "payments")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema paymentsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PaymentsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("uloha4");
        wsdl11Definition.setSchema(paymentsSchema);
        return wsdl11Definition;
    }

    //nastavenie xsd suboru
    @Bean
    public XsdSchema paymentsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("payments.xsd"));
    }

    //konfiguracia klienta
    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("https.courses_fit_cvut_cz.ni_am1.hw.web_services");
        return marshaller;
    }

    @Bean
    public WebServiceClient wsClient(Jaxb2Marshaller marshaller){
        WebServiceClient client = new WebServiceClient();
        client.setDefaultUri("http://147.32.233.18:8888/NI-AM1-CardValidation/ws/");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
