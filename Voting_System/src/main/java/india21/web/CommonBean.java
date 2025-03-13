package india21.web;

import india21.logic.CommonLogic;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class CommonBean {

    @Inject
    private CommonLogic commonLogic;

    @PostConstruct
    public void onStartup() {
        Logger.getLogger("SystemEngine").log(Level.INFO, "Engine Started...");
    }

    @Schedule(dayOfMonth = "*", hour = "*", minute = "*/1", persistent = false)
    public void handlePollStates() {
        Logger.getLogger("SystemEngine").log(Level.INFO, "Engine operateStates...");
        commonLogic.operateStates();
    }

    @Schedule(dayOfMonth = "*", hour = "*/3", persistent = false)
    public void handleNotifications() {
        Logger.getLogger("SystemEngine").log(Level.INFO, "Engine notificationMessages...");
        commonLogic.notificationMessages();
    }
}
