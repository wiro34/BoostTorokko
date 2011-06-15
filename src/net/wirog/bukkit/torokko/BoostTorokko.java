package net.wirog.bukkit.torokko;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * 
 * 
 * @author wiro
 */
public class BoostTorokko extends JavaPlugin  {
	//-----------------------------------------------------------------------------------
	// メソッド
	//-----------------------------------------------------------------------------------
	/**
	 * onEnable
	 */
	@Override
	public void onEnable() {
		// イベントリスナの設定
		VehicleListener vehicleListener   = new VehicleListener();
		getServer().getPluginManager().registerEvent(Type.VEHICLE_MOVE,			vehicleListener,  Priority.Normal, this);

		// ログ表示
		Logger logger = Logger.getLogger(getDescription().getName());
		logger.info(getDescription().getName() + " version " + getDescription().getVersion() + " is enabled.");
	}
	
	/**
	 * onDisable
	 */
	@Override
	public void onDisable() {}

	//-----------------------------------------------------------------------------------
	// 内部クラス
	//-----------------------------------------------------------------------------------
	/**
	 * VehicleListenerの実装
	 */
	public class VehicleListener extends org.bukkit.event.vehicle.VehicleListener{
		@Override
		public void onVehicleMove(VehicleMoveEvent event) {
			Vehicle vehicle = event.getVehicle();
			
			// 乗り物がマインカートで、かつ人が乗っているとき
			if (vehicle instanceof Minecart && vehicle.getPassenger() != null) {
				Block from = event.getFrom().getBlock();
				Block down = from.getFace(BlockFace.DOWN);
				
				// ダイアモンドブロックの上に来たら速度をとりあえず 1000 倍する
				if (down.getType() == Material.DIAMOND_BLOCK) {
					Vector vel = vehicle.getVelocity();
					vel.multiply(1000);
					vehicle.setVelocity(vel);
				}
			}
		}
	}
}
