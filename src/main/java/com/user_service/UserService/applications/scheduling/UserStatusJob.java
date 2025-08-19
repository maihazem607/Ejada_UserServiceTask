package com.user_service.UserService.applications.scheduling;


import com.user_service.UserService.applications.enums.UserStatus;
import com.user_service.UserService.applications.models.User;
import com.user_service.UserService.applications.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class UserStatusJob {
    private static final Logger logger = LoggerFactory.getLogger(UserStatusJob.class);

    @Autowired
    private UserRepository userRepository;


    @Scheduled(cron = "0 */5 * * * *")
    public void updateUserStatus() {
        logger.info("Checking user statuses...");
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        var users = userRepository.findAll();
        for (User user : users) {
            Date userCreatedDate = user.getDateCreated();
            LocalDate userCreatedLocalDate = userCreatedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (userCreatedLocalDate.isBefore(oneMonthAgo)) {
                if (user.getStatus() != UserStatus.INACTIVE) {
                    user.setStatus(UserStatus.INACTIVE);
                    userRepository.save(user);
                    logger.info("User {} set to INACTIVE", user.getUsername());
                }
            }
        }
    }
}
