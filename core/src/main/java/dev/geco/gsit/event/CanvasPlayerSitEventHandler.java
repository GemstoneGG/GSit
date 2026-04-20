package dev.geco.gsit.event;

import dev.geco.gsit.GSitMain;
import dev.geco.gsit.model.StopReason;
import dev.geco.gsit.service.PlayerSitService;
import io.canvasmc.canvas.event.EntityPostPortalAsyncEvent;
import io.canvasmc.canvas.event.EntityPostTeleportAsyncEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CanvasPlayerSitEventHandler implements Listener {

    private final GSitMain gSitMain;

    public CanvasPlayerSitEventHandler(GSitMain gSitMain) {
        this.gSitMain = gSitMain;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityPostTeleportAsync(EntityPostTeleportAsyncEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        handlePostMove(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityPostPortalAsync(EntityPostPortalAsyncEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        handlePostMove(player);
    }

    private void handlePostMove(Player player) {
        if(!player.isValid()) return;
        PlayerSitService service = gSitMain.getPlayerSitService();

        if(service.isPlayerTopOfPlayerSitStack(player)) {
            service.stopPlayerSit(player, StopReason.TELEPORT, false, true, true);
        }

        if(service.isPlayerBottomOfPlayerSitStack(player)) {
            if(player.getPassengers().isEmpty()) {
                service.stopPlayerSit(player, StopReason.TELEPORT, true, false, false);
            }
        }
    }

}