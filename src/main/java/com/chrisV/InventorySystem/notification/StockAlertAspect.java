package com.chrisV.InventorySystem.notification;

import com.chrisV.InventorySystem.model.EmailDetails;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.service.EmailService;
import com.chrisV.InventorySystem.repo.ProductRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class StockAlertAspect {

    @Autowired
    private ProductRepo repo;

    private final EmailService emailService;

    //testing
    private static final Logger LOGGER = LoggerFactory.getLogger(StockAlertAspect.class);

    public StockAlertAspect(EmailService emailService) {
        this.emailService = emailService;
    }

    @Value("${spring.mail.username}")
    private String RECIPIENT_EMAIL;
    private final int STOCK_THRESHOLD = 10; // Define your stock threshold here
    private final String MAIL_MESSAGE = "Stock for product %s is below the threshold. Current stock: %d";
    private final String MAIL_SUBJECT = "Stock Alert for Product %s";

    // Adjust the pointcut to match your command method
    @Before("execution(* com.chrisV.InventorySystem.service.ProductService.usageUpdate(..)) && args(name,..)")
    public void sendStockAlertIfNeeded(String name) {
        Product product = repo.findByName(name);
        //int currentStock = product.getStock();

        if(product.getStock() <=10) {
            String status = emailService.sendSimpleEmail(EmailDetails.builder()
                    .recipient(RECIPIENT_EMAIL)
                    .subject(MAIL_SUBJECT.formatted("[" + product.getName()).toUpperCase() + "]")
                    .msgBody(MAIL_MESSAGE.formatted(("[" + product.getName().toUpperCase() + "]"), product.getStock()))
                    .build());
            LOGGER.info(status);
        }
    }

    @Around("execution(* com.chrisV.InventorySystem.service.ProductService.*(..))")
    public Object monitorTime(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        //method execution

        Object obj = jp.proceed();

        long end = System.currentTimeMillis();
        LOGGER.info("Time taken : " + (end - start) + " ms " + jp.getSignature().getName());
        return obj;
    }
}
