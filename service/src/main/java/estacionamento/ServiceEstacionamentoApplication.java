package estacionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceEstacionamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceEstacionamentoApplication.class, args);
	}

}
