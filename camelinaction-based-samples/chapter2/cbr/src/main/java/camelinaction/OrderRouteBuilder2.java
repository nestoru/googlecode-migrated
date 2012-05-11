package camelinaction;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Code pulled out of OrderRoute to be used from OrderRoute2
 *
 * <quote>
 * A set of routes that watches a directory for new orders, reads them, converts the order 
 * file into a JMS Message and then sends it to the JMS incomingOrders queue hosted 
 * on an embedded ActiveMQ broker instance.
 * 
 * From there a content-based router is used to send the order to either the
 * xmlOrders or csvOrders queue.
 *
 * 
 * </quote>
 * @author nestor.urquiza
 *
 */
public class OrderRouteBuilder2 extends RouteBuilder {
	@Override
    public void configure() {
        // load file orders from src/data into the JMS queue
        from("file:src/data?noop=true").to("jms:incomingOrders");

        // content-based router
        from("jms:incomingOrders")
        .choice()
            .when(header("CamelFileName").endsWith(".xml"))
                .to("jms:xmlOrders")  
            .when(header("CamelFileName").endsWith(".csv"))
                .to("jms:csvOrders");
        
        // test that our route is working
        from("jms:xmlOrders").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("Received XML order: " 
                        + exchange.getIn().getHeader("CamelFileName"));   
            }
        });                
        from("jms:csvOrders").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("Received CSV order: " 
                        + exchange.getIn().getHeader("CamelFileName"));   
            }
        });
    }
}