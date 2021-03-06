package com.Ben12345rocks.AdvancedCore.Objects;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.Listeners.PlayerRewardEvent;
import com.Ben12345rocks.AdvancedCore.Util.Effects.FireworkHandler;
import com.Ben12345rocks.AdvancedCore.Util.Item.ItemBuilder;
import com.Ben12345rocks.AdvancedCore.Util.Javascript.JavascriptHandler;
import com.Ben12345rocks.AdvancedCore.Util.Misc.ArrayUtils;
import com.Ben12345rocks.AdvancedCore.Util.Misc.MiscUtils;
import com.Ben12345rocks.AdvancedCore.Util.Misc.PlayerUtils;
import com.Ben12345rocks.AdvancedCore.Util.Misc.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Reward.
 */
public class Reward {

	/** The plugin. */
	AdvancedCoreHook plugin = AdvancedCoreHook.getInstance();

	/** The name. */
	public String name;

	/** The file data. */
	private RewardFileData fileData;

	/** The reward type. */
	private String rewardType;

	/** The delay enabled. */
	private boolean delayEnabled;

	/** The delay hours. */
	private int delayHours;

	/** The delay minutes. */
	private int delayMinutes;

	/** The timed enabled. */
	private boolean timedEnabled;

	/** The timed hour. */
	private int timedHour;

	/** The timed minute. */
	private int timedMinute;

	/** The chance. */
	private double chance;

	/** The random chance. */
	private double randomChance;

	/** The random rewards. */
	private ArrayList<String> randomRewards;

	/** The random fall back. */
	private ArrayList<String> randomFallBack;

	/** The require permission. */
	private boolean requirePermission;

	/** The worlds. */
	private ArrayList<String> worlds;

	/** The items. */
	private Set<String> items;

	/** The money. */
	private int money;

	/** The Min money. */
	private int MinMoney;

	/** The Max money. */
	private int MaxMoney;

	/** The exp. */
	private int exp;

	/** The min exp. */
	private int minExp;

	/** The max exp. */
	private int maxExp;

	/** The console commands. */
	private ArrayList<String> consoleCommands;

	/** The player commands. */
	private ArrayList<String> playerCommands;

	/** The potions. */
	private Set<String> potions;

	/** The potions duration. */
	private HashMap<String, Integer> potionsDuration;

	/** The potions amplifier. */
	private HashMap<String, Integer> potionsAmplifier;

	/** The reward msg. */
	private String rewardMsg;

	/** The action bar msg. */
	private String actionBarMsg;

	/** The action bar delay. */
	private int actionBarDelay;

	/** The boss bar enabled. */
	private boolean bossBarEnabled;

	/** The boss bar message. */
	private String bossBarMessage;

	/** The boss bar color. */
	private String bossBarColor;

	/** The boss bar style. */
	private String bossBarStyle;

	/** The boss bar delay. */
	private int bossBarDelay;

	/** The boss bar progress. */
	private double bossBarProgress;

	/** The broadcast msg. */
	private String broadcastMsg;

	/** The permission. */
	private String permission;

	/** The javascript enabled. */
	private boolean javascriptEnabled;

	/** The javascript expression. */
	private String javascriptExpression;

	/** The javascript true rewards. */
	private ArrayList<String> javascriptTrueRewards;

	/** The javascript false rewards. */
	private ArrayList<String> javascriptFalseRewards;

	/** The choice rewards rewards. */
	private ArrayList<String> choiceRewardsRewards;

	/** The items and amounts given. */
	private HashMap<String, Integer> itemsAndAmountsGiven;

	/** The choice rewards enabled. */
	private boolean choiceRewardsEnabled;

	/** The firework enabled. */
	private boolean fireworkEnabled;

	/** The firework flicker. */
	private boolean fireworkFlicker;

	/** The firework trail. */
	private boolean fireworkTrail;

	/** The firework power. */
	private int fireworkPower;

	/** The firework colors. */
	private ArrayList<String> fireworkColors;

	/** The firework fade out colors. */
	private ArrayList<String> fireworkFadeOutColors;

	/** The firework types. */
	private ArrayList<String> fireworkTypes;

	/** The uses worlds. */
	private boolean usesWorlds;

	/** The title enabled. */
	private boolean titleEnabled;

	/** The title title. */
	private String titleTitle;

	/** The title sub title. */
	private String titleSubTitle;

	/** The title fade in. */
	private int titleFadeIn;

	/** The title show time. */
	private int titleShowTime;

	/** The title fade out. */
	private int titleFadeOut;

	/** The sound enabled. */
	private boolean soundEnabled;

	/** The sound sound. */
	private String soundSound;

	/** The sound volume. */
	private float soundVolume;

	/** The sound pitch. */
	private float soundPitch;

	/** The effect enabled. */
	private boolean effectEnabled;

	/** The effect effect. */
	private String effectEffect;

	/** The effect data. */
	private int effectData;

	/** The effect particles. */
	private int effectParticles;

	/** The effect radius. */
	private int effectRadius;

	/** The file. */
	private File file;

	/**
	 * Instantiates a new reward.
	 *
	 * @param file
	 *            the file
	 * @param reward
	 *            the reward
	 */
	public Reward(File file, String reward) {
		load(file, reward);
	}

	/**
	 * Instantiates a new reward.
	 *
	 * @param reward
	 *            the reward
	 */
	@Deprecated
	public Reward(String reward) {
		load(new File(plugin.getPlugin().getDataFolder(), "Rewards"), reward);
	}

	/**
	 * Check chance.
	 *
	 * @return true, if successful
	 */
	public boolean checkChance() {
		double chance = getChance();

		if ((chance == 0) || (chance == 100)) {
			return true;
		}

		double randomNum = ThreadLocalRandom.current().nextDouble(chance + 1);

		plugin.debug("Random: " + randomNum + ", Chance: " + chance);

		if (randomNum <= chance) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check choice rewards.
	 *
	 * @param user
	 *            the user
	 */
	public void checkChoiceRewards(User user) {
		if (isChoiceRewardsEnabled()) {
			Player player = user.getPlayer();
			if (player != null) {
				user.addChoiceReward(this);
			}
		}
	}

	/**
	 * Check delayed.
	 *
	 * @param user
	 *            the user
	 * @return true, if successful
	 */
	public boolean checkDelayed(User user) {
		if (!isDelayEnabled()) {
			return false;
		}

		LocalDateTime time = LocalDateTime.now();
		time = time.plus(getDelayHours(), ChronoUnit.HOURS);
		time = time.plus(getDelayMinutes(), ChronoUnit.MINUTES);
		user.addTimedReward(this, time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		plugin.debug("Giving reward " + name + " in " + getDelayHours() + " hours " + getDelayMinutes() + " minutes ("
				+ time.toString() + ")");
		return true;

	}

	/**
	 * Check random chance.
	 *
	 * @return true, if successful
	 */
	public boolean checkRandomChance() {
		double chance = getRandomChance();

		if ((chance == 0) || (chance == 100)) {
			return true;
		}

		double randomNum = ThreadLocalRandom.current().nextDouble(chance + 1);

		plugin.debug("Random: Random: " + randomNum + ", Chance: " + chance);

		if (randomNum <= chance) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check timed.
	 *
	 * @param user
	 *            the user
	 * @return true, if successful
	 */
	public boolean checkTimed(User user) {
		if (!isTimedEnabled()) {
			return false;
		}

		LocalDateTime time = LocalDateTime.now();
		time = time.withHour(getTimedHour());
		time = time.withMinute(getTimedMinute());

		if (LocalDateTime.now().isAfter(time)) {
			time = time.plusDays(1);
		}
		user.addTimedReward(this, time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		plugin.debug("Giving reward " + name + " at " + time.toString());
		return true;
	}

	public boolean checkWorld(User user) {
		if (!isUsesWorlds()) {
			return true;
		}

		Player player = user.getPlayer();
		if (player == null) {
			return false;
		}
		String world = player.getWorld().getName();
		if (getWorlds().contains(world)) {
			return true;
		}

		return false;
	}

	/**
	 * Gets the action bar delay.
	 *
	 * @return the action bar delay
	 */
	public int getActionBarDelay() {
		return actionBarDelay;
	}

	/**
	 * Gets the action bar msg.
	 *
	 * @return the action bar msg
	 */
	public String getActionBarMsg() {
		return actionBarMsg;
	}

	/**
	 * Gets the boss bar color.
	 *
	 * @return the boss bar color
	 */
	public String getBossBarColor() {
		return bossBarColor;
	}

	/**
	 * Gets the boss bar delay.
	 *
	 * @return the boss bar delay
	 */
	public int getBossBarDelay() {
		return bossBarDelay;
	}

	/**
	 * Gets the boss bar message.
	 *
	 * @return the boss bar message
	 */
	public String getBossBarMessage() {
		return bossBarMessage;
	}

	/**
	 * Gets the boss bar progress.
	 *
	 * @return the boss bar progress
	 */
	public double getBossBarProgress() {
		return bossBarProgress;
	}

	/**
	 * Gets the boss bar style.
	 *
	 * @return the boss bar style
	 */
	public String getBossBarStyle() {
		return bossBarStyle;
	}

	/**
	 * Gets the broadcast msg.
	 *
	 * @return the broadcast msg
	 */
	public String getBroadcastMsg() {
		return broadcastMsg;
	}

	/**
	 * Gets the chance.
	 *
	 * @return the chance
	 */
	public double getChance() {
		return chance;
	}

	/**
	 * Gets the choice rewards rewards.
	 *
	 * @return the choice rewards rewards
	 */
	public ArrayList<String> getChoiceRewardsRewards() {
		return choiceRewardsRewards;
	}

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public RewardFileData getConfig() {
		return fileData;
	}

	/**
	 * Gets the console commands.
	 *
	 * @return the console commands
	 */
	public ArrayList<String> getConsoleCommands() {
		return consoleCommands;
	}

	/**
	 * Gets the delay hours.
	 *
	 * @return the delay hours
	 */
	public int getDelayHours() {
		return delayHours;
	}

	/**
	 * Gets the delay minutes.
	 *
	 * @return the delay minutes
	 */
	public int getDelayMinutes() {
		return delayMinutes;
	}

	/**
	 * Gets the effect data.
	 *
	 * @return the effect data
	 */
	public int getEffectData() {
		return effectData;
	}

	/**
	 * Gets the effect effect.
	 *
	 * @return the effect effect
	 */
	public String getEffectEffect() {
		return effectEffect;
	}

	/**
	 * Gets the effect particles.
	 *
	 * @return the effect particles
	 */
	public int getEffectParticles() {
		return effectParticles;
	}

	/**
	 * Gets the effect radius.
	 *
	 * @return the effect radius
	 */
	public int getEffectRadius() {
		return effectRadius;
	}

	/**
	 * Gets the exp.
	 *
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * Gets the exp to give.
	 *
	 * @return the exp to give
	 */
	public int getExpToGive() {
		int amount = getExp();
		int maxAmount = getMaxExp();
		int minAmount = getMinExp();
		if ((maxAmount == 0) && (minAmount == 0)) {
			return amount;
		} else {
			int num = (int) (Math.random() * maxAmount);
			num++;
			if (num < minAmount) {
				num = minAmount;
			}
			return num;
		}
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Gets the firework colors.
	 *
	 * @return the firework colors
	 */
	public ArrayList<String> getFireworkColors() {
		return fireworkColors;
	}

	/**
	 * Gets the firework fade out colors.
	 *
	 * @return the firework fade out colors
	 */
	public ArrayList<String> getFireworkFadeOutColors() {
		return fireworkFadeOutColors;
	}

	/**
	 * Gets the firework power.
	 *
	 * @return the firework power
	 */
	public int getFireworkPower() {
		return fireworkPower;
	}

	/**
	 * Gets the firework types.
	 *
	 * @return the firework types
	 */
	public ArrayList<String> getFireworkTypes() {
		return fireworkTypes;
	}

	public ItemStack getItem() {
		return new ItemStack(Material.STONE);
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public Set<String> getItems() {
		return items;
	}

	/**
	 * Gets the items and amounts given.
	 *
	 * @return the items and amounts given
	 */
	public HashMap<String, Integer> getItemsAndAmountsGiven() {
		return itemsAndAmountsGiven;
	}

	public ItemStack getItemStack(User user, String item) {
		return new ItemBuilder(getConfig().getItemSection(item)).setSkullOwner(user.getPlayerName()).toItemStack();
	}

	/**
	 * Gets the javascript expression.
	 *
	 * @return the javascript expression
	 */
	public String getJavascriptExpression() {
		return javascriptExpression;
	}

	/**
	 * Gets the javascript false rewards.
	 *
	 * @return the javascript false rewards
	 */
	public ArrayList<String> getJavascriptFalseRewards() {
		return javascriptFalseRewards;
	}

	/**
	 * Gets the javascript true rewards.
	 *
	 * @return the javascript true rewards
	 */
	public ArrayList<String> getJavascriptTrueRewards() {
		return javascriptTrueRewards;
	}

	/**
	 * Gets the max exp.
	 *
	 * @return the max exp
	 */
	public int getMaxExp() {
		return maxExp;
	}

	/**
	 * Gets the max money.
	 *
	 * @return the max money
	 */
	public int getMaxMoney() {
		return MaxMoney;
	}

	/**
	 * Gets the min exp.
	 *
	 * @return the min exp
	 */
	public int getMinExp() {
		return minExp;
	}

	/**
	 * Gets the min money.
	 *
	 * @return the min money
	 */
	public int getMinMoney() {
		return MinMoney;
	}

	/**
	 * Gets the money.
	 *
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Gets the money to give.
	 *
	 * @return the money to give
	 */
	public int getMoneyToGive() {
		int amount = getMoney();
		int maxAmount = getMaxMoney();
		int minAmount = getMinMoney();
		if ((maxAmount == 0) && (minAmount == 0)) {
			return amount;
		} else {
			int num = (int) (Math.random() * maxAmount);
			num++;
			if (num < minAmount) {
				num = minAmount;
			}
			return num;
		}
	}

	/**
	 * Gets the permission.
	 *
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Gets the player commands.
	 *
	 * @return the player commands
	 */
	public ArrayList<String> getPlayerCommands() {
		return playerCommands;
	}

	/**
	 * Gets the potions.
	 *
	 * @return the potions
	 */
	public Set<String> getPotions() {
		return potions;
	}

	/**
	 * Gets the potions amplifier.
	 *
	 * @return the potions amplifier
	 */
	public HashMap<String, Integer> getPotionsAmplifier() {
		return potionsAmplifier;
	}

	/**
	 * Gets the potions duration.
	 *
	 * @return the potions duration
	 */
	public HashMap<String, Integer> getPotionsDuration() {
		return potionsDuration;
	}

	/**
	 * Gets the random chance.
	 *
	 * @return the random chance
	 */
	public double getRandomChance() {
		return randomChance;
	}

	/**
	 * Gets the random fall back.
	 *
	 * @return the random fall back
	 */
	public ArrayList<String> getRandomFallBack() {
		return randomFallBack;
	}

	/**
	 * Gets the random rewards.
	 *
	 * @return the random rewards
	 */
	public ArrayList<String> getRandomRewards() {
		return randomRewards;
	}

	/**
	 * Gets the reward msg.
	 *
	 * @return the reward msg
	 */
	public String getRewardMsg() {
		return rewardMsg;
	}

	/**
	 * Gets the reward name.
	 *
	 * @return the reward name
	 */
	public String getRewardName() {
		return name;
	}

	/**
	 * Gets the reward type.
	 *
	 * @return the reward type
	 */
	public String getRewardType() {
		return rewardType;
	}

	/**
	 * Gets the sound pitch.
	 *
	 * @return the sound pitch
	 */
	public double getSoundPitch() {
		return soundPitch;
	}

	/**
	 * Gets the sound sound.
	 *
	 * @return the sound sound
	 */
	public String getSoundSound() {
		return soundSound;
	}

	/**
	 * Gets the sound volume.
	 *
	 * @return the sound volume
	 */
	public double getSoundVolume() {
		return soundVolume;
	}

	/**
	 * Gets the timed hour.
	 *
	 * @return the timed hour
	 */
	public int getTimedHour() {
		return timedHour;
	}

	/**
	 * Gets the timed minute.
	 *
	 * @return the timed minute
	 */
	public int getTimedMinute() {
		return timedMinute;
	}

	/**
	 * Gets the title fade in.
	 *
	 * @return the title fade in
	 */
	public int getTitleFadeIn() {
		return titleFadeIn;
	}

	/**
	 * Gets the title fade out.
	 *
	 * @return the title fade out
	 */
	public int getTitleFadeOut() {
		return titleFadeOut;
	}

	/**
	 * Gets the title show time.
	 *
	 * @return the title show time
	 */
	public int getTitleShowTime() {
		return titleShowTime;
	}

	/**
	 * Gets the title sub title.
	 *
	 * @return the title sub title
	 */
	public String getTitleSubTitle() {
		return titleSubTitle;
	}

	/**
	 * Gets the title title.
	 *
	 * @return the title title
	 */
	public String getTitleTitle() {
		return titleTitle;
	}

	/**
	 * Gets the worlds.
	 *
	 * @return the worlds
	 */
	public ArrayList<String> getWorlds() {
		return worlds;
	}

	/**
	 * Give exp.
	 *
	 * @param user
	 *            the user
	 * @param exp
	 *            the exp
	 */
	public void giveExp(User user, int exp) {
		user.giveExp(exp);
	}

	/**
	 * Give items.
	 *
	 * @param user
	 *            the user
	 */
	public void giveItems(User user) {
		itemsAndAmountsGiven = new HashMap<String, Integer>();
		for (String item : getItems()) {
			user.giveItem(getItemStack(user, item));
		}
	}

	/**
	 * Give money.
	 *
	 * @param user
	 *            the user
	 * @param money
	 *            the money
	 */
	public void giveMoney(User user, int money) {
		user.giveMoney(money);
	}

	/**
	 * Give potions.
	 *
	 * @param user
	 *            the user
	 */
	public void givePotions(User user) {
		for (String potionName : getPotions()) {
			user.givePotionEffect(potionName, getPotionsDuration().get(potionName),
					getPotionsAmplifier().get(potionName));
		}
	}

	/**
	 * Give random.
	 *
	 * @param user
	 *            the user
	 * @param online
	 *            the online
	 */
	public void giveRandom(User user, boolean online) {
		if (checkRandomChance()) {
			ArrayList<String> rewards = getRandomRewards();
			if (rewards != null) {
				if (rewards.size() > 0) {
					String reward = rewards.get(ThreadLocalRandom.current().nextInt(rewards.size()));
					if (!reward.equals("")) {
						RewardHandler.getInstance().giveReward(user, reward, online);
					}
				}
			}
		} else {
			for (String reward : getRandomFallBack()) {
				if (!reward.equals("")) {
					RewardHandler.getInstance().giveReward(user, reward, online);
				}
			}
		}
	}

	/**
	 * Give reward.
	 *
	 * @param user
	 *            the user
	 * @param online
	 *            the online
	 */
	public void giveReward(User user, boolean online) {
		giveReward(user, online, true);
	}

	/**
	 * Give reward.
	 *
	 * @param user
	 *            the user
	 * @param online
	 *            the online
	 * @param giveOffline
	 *            the give offline
	 */
	public void giveReward(User user, boolean online, boolean giveOffline) {

		PlayerRewardEvent event = new PlayerRewardEvent(this, user);
		Bukkit.getPluginManager().callEvent(event);

		if (event.isCancelled()) {
			plugin.debug("Reward " + name + " was cancelled");
			return;
		}

		if ((!online && !user.isOnline()) || !checkWorld(user)) {
			if (giveOffline) {
				user.addOfflineRewards(this);
			}
			return;
		}

		if (checkDelayed(user)) {
			return;
		}

		if (checkTimed(user)) {
			return;
		}

		giveRewardReward(user, online);
	}

	/**
	 * Give reward reward.
	 *
	 * @param user
	 *            the user
	 * @param online
	 *            the online
	 */
	public void giveRewardReward(User user, boolean online) {
		plugin.debug("Attempting to give " + user.getPlayerName() + " reward " + name);

		String type = getRewardType();
		if (online) {
			if (type.equalsIgnoreCase("offline")) {
				plugin.debug("Reward Type Don't match");
				return;
			}
		} else {
			if (type.equalsIgnoreCase("online")) {
				plugin.debug("Reward Type Don't match");
				return;
			}
		}

		if (checkChance()) {
			giveRewardUser(user);
		}
	}

	/**
	 * Give reward user.
	 *
	 * @param user
	 *            the user
	 */
	public void giveRewardUser(User user) {
		Player player = user.getPlayer();
		if (player != null) {
			if (hasPermission(user)) {
				giveRandom(user, true);
				runJavascript(user, true);
				int money = getMoneyToGive();
				giveMoney(user, money);
				giveItems(user);
				int exp = getExpToGive();
				giveExp(user, exp);
				runCommands(user);
				givePotions(user);
				sendTitle(user);
				sendActionBar(user);
				playSound(user);
				playEffect(user);
				sendBossBar(user);
				sendMessage(user, money, exp);
				checkChoiceRewards(user);
				sendFirework(user);

				plugin.debug("Gave " + user.getPlayerName() + " reward " + name);

			}
		}
	}

	/**
	 * Checks for permission.
	 *
	 * @param user
	 *            the user
	 * @return true, if successful
	 */
	public boolean hasPermission(User user) {
		if (!isRequirePermission()) {
			return true;
		}
		return PlayerUtils.getInstance().hasServerPermission(user.getPlayerName(), permission);
	}

	/**
	 * Checks if is boss bar enabled.
	 *
	 * @return true, if is boss bar enabled
	 */
	public boolean isBossBarEnabled() {
		return bossBarEnabled;
	}

	/**
	 * Checks if is choice rewards enabled.
	 *
	 * @return true, if is choice rewards enabled
	 */
	public boolean isChoiceRewardsEnabled() {
		return choiceRewardsEnabled;
	}

	/**
	 * Checks if is delay enabled.
	 *
	 * @return true, if is delay enabled
	 */
	public boolean isDelayEnabled() {
		return delayEnabled;
	}

	/**
	 * Checks if is effect enabled.
	 *
	 * @return true, if is effect enabled
	 */
	public boolean isEffectEnabled() {
		return effectEnabled;
	}

	/**
	 * Checks if is firework enabled.
	 *
	 * @return true, if is firework enabled
	 */
	public boolean isFireworkEnabled() {
		return fireworkEnabled;
	}

	/**
	 * Checks if is firework flicker.
	 *
	 * @return true, if is firework flicker
	 */
	public boolean isFireworkFlicker() {
		return fireworkFlicker;
	}

	/**
	 * Checks if is firework trail.
	 *
	 * @return true, if is firework trail
	 */
	public boolean isFireworkTrail() {
		return fireworkTrail;
	}

	/**
	 * Checks if is javascript enabled.
	 *
	 * @return true, if is javascript enabled
	 */
	public boolean isJavascriptEnabled() {
		return javascriptEnabled;
	}

	/**
	 * Checks if is require permission.
	 *
	 * @return true, if is require permission
	 */
	public boolean isRequirePermission() {
		return requirePermission;
	}

	/**
	 * Checks if is sound enabled.
	 *
	 * @return true, if is sound enabled
	 */
	public boolean isSoundEnabled() {
		return soundEnabled;
	}

	/**
	 * Checks if is timed enabled.
	 *
	 * @return true, if is timed enabled
	 */
	public boolean isTimedEnabled() {
		return timedEnabled;
	}

	/**
	 * Checks if is title enabled.
	 *
	 * @return true, if is title enabled
	 */
	public boolean isTitleEnabled() {
		return titleEnabled;
	}

	/**
	 * Checks if is uses worlds.
	 *
	 * @return true, if is uses worlds
	 */
	public boolean isUsesWorlds() {
		return usesWorlds;
	}

	/**
	 * Load.
	 *
	 * @param folder
	 *            the folder
	 * @param reward
	 *            the reward
	 */
	public void load(File folder, String reward) {
		name = reward;
		file = folder;
		fileData = new RewardFileData(this, getFile());
		setRewardType(getConfig().getRewardType());

		setDelayEnabled(getConfig().getDelayedEnabled());
		setDelayHours(getConfig().getDelayedHours());
		setDelayMinutes(getConfig().getDelayedMinutes());

		setTimedEnabled(getConfig().getTimedEnabled());
		setTimedHour(getConfig().getTimedHour());
		setTimedMinute(getConfig().getTimedMinute());

		setChance(getConfig().getChance());
		setRandomChance(getConfig().getRandomChance());
		setRandomRewards(getConfig().getRandomRewards());
		setRandomFallBack(getConfig().getRandomFallBack());

		setRequirePermission(getConfig().getRequirePermission());
		setWorlds(getConfig().getWorlds());

		setItems(getConfig().getItems());

		setMoney(getConfig().getMoney());
		setMinMoney(getConfig().getMinMoney());
		setMaxMoney(getConfig().getMaxMoney());

		setExp(getConfig().getEXP());
		setMinExp(getConfig().getMinExp());
		setMaxExp(getConfig().getMaxExp());

		setConsoleCommands(getConfig().getCommandsConsole());
		setPlayerCommands(getConfig().getCommandsPlayer());

		potions = getConfig().getPotions();
		potionsDuration = new HashMap<String, Integer>();
		potionsAmplifier = new HashMap<String, Integer>();
		for (String potion : potions) {
			potionsDuration.put(potion, getConfig().getPotionsDuration(potion));
			potionsAmplifier.put(potion, getConfig().getPotionsAmplifier(potion));
		}

		setRewardMsg(getConfig().getMessagesReward());
		setActionBarMsg(getConfig().getActionBarMessage());
		setActionBarDelay(getConfig().getActionBarDelay());

		setBossBarEnabled(getConfig().getBossBarEnabled());
		setBossBarMessage(getConfig().getBossBarMessage());
		setBossBarColor(getConfig().getBossBarColor());
		setBossBarStyle(getConfig().getBossBarStyle());
		setBossBarProgress(getConfig().getBossBarProgress());
		setBossBarDelay(getConfig().getBossBarDelay());

		broadcastMsg = getConfig().getMessagesBroadcast();

		permission = getConfig().getPermission();

		setJavascriptEnabled(getConfig().getJavascriptEnabled());
		setJavascriptExpression(getConfig().getJavascriptExpression());
		setJavascriptTrueRewards(getConfig().getJavascriptTrueRewards());
		setJavascriptFalseRewards(getConfig().getJavascriptFalseRewards());
		setChoiceRewardsEnabled(getConfig().getChoiceRewardsEnabled());
		setChoiceRewardsRewards(getConfig().getChoiceRewardsRewards());

		fireworkEnabled = getConfig().getFireworkEnabled();
		fireworkColors = getConfig().getFireworkColors();
		fireworkFadeOutColors = getConfig().getFireworkColorsFadeOut();
		fireworkPower = getConfig().getFireworkPower();
		fireworkTypes = getConfig().getFireworkTypes();
		fireworkTrail = getConfig().getFireworkTrail();
		fireworkFlicker = getConfig().getFireworkFlicker();

		if (getWorlds().size() == 0) {
			usesWorlds = false;
		} else {
			usesWorlds = true;
		}

		titleEnabled = getConfig().getTitleEnabled();
		titleTitle = getConfig().getTitleTitle();
		titleSubTitle = getConfig().getTitleSubTitle();
		titleFadeIn = getConfig().getTitleFadeIn();
		titleShowTime = getConfig().getTitleShowTime();
		titleFadeOut = getConfig().getTitleFadeOut();

		soundEnabled = getConfig().getSoundEnabled();
		soundSound = getConfig().getSoundSound();
		soundPitch = getConfig().getSoundPitch();
		soundVolume = getConfig().getSoundVolume();

		effectEnabled = getConfig().getEffectEnabled();
		effectEffect = getConfig().getEffectEffect();
		effectData = getConfig().getEffectData();
		effectParticles = getConfig().getEffectParticles();
		effectRadius = getConfig().getEffectRadius();

	}

	/**
	 * Play effect.
	 *
	 * @param user
	 *            the user
	 */
	public void playEffect(User user) {
		if (effectEnabled) {
			user.playParticleEffect(effectEffect, effectData, effectParticles, effectRadius);
		}
	}

	/**
	 * Play sound.
	 *
	 * @param user
	 *            the user
	 */
	public void playSound(User user) {
		if (soundEnabled) {
			try {
				user.playSound(soundSound, soundVolume, soundPitch);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Run commands.
	 *
	 * @param user
	 *            the user
	 */
	public void runCommands(User user) {
		String playerName = user.getPlayerName();

		// Console commands
		ArrayList<String> consolecmds = getConsoleCommands();

		if (consolecmds != null) {
			for (String consolecmd : consolecmds) {
				if (consolecmd.length() > 0) {
					consolecmd = consolecmd.replace("%player%", playerName);
					final String cmd = consolecmd;
					Bukkit.getScheduler().runTask(plugin.getPlugin(), new Runnable() {

						@Override
						public void run() {
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
						}
					});

				}
			}
		}

		// Player commands
		ArrayList<String> playercmds = getPlayerCommands();

		Player player = user.getPlayer();
		if (playercmds != null) {
			for (String playercmd : playercmds) {
				if ((player != null) && (playercmd.length() > 0)) {
					playercmd = playercmd.replace("%player%", playerName);
					final String cmd = playercmd;
					Bukkit.getScheduler().runTask(plugin.getPlugin(), new Runnable() {

						@Override
						public void run() {
							player.performCommand(cmd);
						}
					});

				}
			}
		}
	}

	/**
	 * Run javascript.
	 *
	 * @param user
	 *            the user
	 * @param online
	 *            the online
	 */
	public void runJavascript(User user, boolean online) {
		if (isJavascriptEnabled()) {
			if (JavascriptHandler.getInstance().evalute(user, getJavascriptExpression())) {
				for (String reward : getJavascriptTrueRewards()) {
					RewardHandler.getInstance().giveReward(user, reward, online);
				}
			} else {
				for (String reward : getJavascriptFalseRewards()) {
					RewardHandler.getInstance().giveReward(user, reward, online);
				}
			}
		}
	}

	/**
	 * Send action bar.
	 *
	 * @param user
	 *            the user
	 */
	public void sendActionBar(User user) {
		user.sendActionBar(getActionBarMsg(), getActionBarDelay());
	}

	/**
	 * Send boss bar.
	 *
	 * @param user
	 *            the user
	 */
	public void sendBossBar(User user) {
		if (isBossBarEnabled()) {
			user.sendBossBar(getBossBarMessage(), getBossBarColor(), getBossBarStyle(), getBossBarProgress(),
					getBossBarDelay());
		}
	}

	/**
	 * Send firework.
	 *
	 * @param user
	 *            the user
	 */
	public void sendFirework(User user) {
		if (isFireworkEnabled()) {
			FireworkHandler.getInstance().launchFirework(user.getPlayer().getLocation(), getFireworkPower(),
					getFireworkColors(), getFireworkFadeOutColors(), isFireworkTrail(), isFireworkFlicker(),
					getFireworkTypes());
		}
	}

	/**
	 * Send message.
	 *
	 * @param user
	 *            the user
	 * @param money
	 *            the money
	 * @param exp
	 *            the exp
	 */
	public void sendMessage(User user, int money, int exp) {
		ArrayList<String> itemsAndAmounts = new ArrayList<String>();
		for (Entry<String, Integer> entry : itemsAndAmountsGiven.entrySet()) {
			itemsAndAmounts.add(entry.getValue() + " " + entry.getKey());
		}
		String itemsAndAmountsMsg = ArrayUtils.getInstance().makeStringList(itemsAndAmounts);

		String broadcastMsg = this.broadcastMsg;
		broadcastMsg = StringUtils.getInstance().replacePlaceHolder(broadcastMsg, "player", user.getPlayerName());

		broadcastMsg = StringUtils.getInstance().replacePlaceHolder(broadcastMsg, "money", "" + money);
		broadcastMsg = StringUtils.getInstance().replacePlaceHolder(broadcastMsg, "exp", "" + exp);
		broadcastMsg = StringUtils.getInstance().replacePlaceHolder(broadcastMsg, "itemsandamount", itemsAndAmountsMsg);
		broadcastMsg = StringUtils.getInstance().replacePlaceHolder(broadcastMsg, "items",
				ArrayUtils.getInstance().makeStringList(ArrayUtils.getInstance().convert(getItems())));

		MiscUtils.getInstance()
				.broadcast(StringUtils.getInstance().replacePlaceHolders(user.getPlayer(), broadcastMsg));

		String msg = StringUtils.getInstance().replacePlaceHolder(rewardMsg, "player", user.getPlayerName());

		msg = StringUtils.getInstance().replacePlaceHolder(msg, "money", "" + money);
		msg = StringUtils.getInstance().replacePlaceHolder(msg, "exp", "" + exp);
		msg = StringUtils.getInstance().replacePlaceHolder(msg, "itemsandamount", itemsAndAmountsMsg);
		msg = StringUtils.getInstance().replacePlaceHolder(msg, "items",
				ArrayUtils.getInstance().makeStringList(ArrayUtils.getInstance().convert(getItems())));

		user.sendMessage(msg);

	}

	/**
	 * Send title.
	 *
	 * @param user
	 *            the user
	 */
	public void sendTitle(User user) {
		if (titleEnabled) {
			user.sendTitle(titleTitle,

					titleSubTitle,

					titleFadeIn, titleShowTime, titleFadeOut);
		}
	}

	/**
	 * Sets the action bar delay.
	 *
	 * @param actionBarDelay
	 *            the new action bar delay
	 */
	public void setActionBarDelay(int actionBarDelay) {
		this.actionBarDelay = actionBarDelay;
	}

	/**
	 * Sets the action bar msg.
	 *
	 * @param actionBarMsg
	 *            the new action bar msg
	 */
	public void setActionBarMsg(String actionBarMsg) {
		this.actionBarMsg = actionBarMsg;
	}

	/**
	 * Sets the boss bar color.
	 *
	 * @param bossBarColor
	 *            the new boss bar color
	 */
	public void setBossBarColor(String bossBarColor) {
		this.bossBarColor = bossBarColor;
	}

	/**
	 * Sets the boss bar delay.
	 *
	 * @param bossBarDelay
	 *            the new boss bar delay
	 */
	public void setBossBarDelay(int bossBarDelay) {
		this.bossBarDelay = bossBarDelay;
	}

	/**
	 * Sets the boss bar enabled.
	 *
	 * @param bossBarEnabled
	 *            the new boss bar enabled
	 */
	public void setBossBarEnabled(boolean bossBarEnabled) {
		this.bossBarEnabled = bossBarEnabled;
	}

	/**
	 * Sets the boss bar message.
	 *
	 * @param bossBarMessage
	 *            the new boss bar message
	 */
	public void setBossBarMessage(String bossBarMessage) {
		this.bossBarMessage = bossBarMessage;
	}

	/**
	 * Sets the boss bar progress.
	 *
	 * @param bossBarProgress
	 *            the new boss bar progress
	 */
	public void setBossBarProgress(double bossBarProgress) {
		this.bossBarProgress = bossBarProgress;
	}

	/**
	 * Sets the boss bar style.
	 *
	 * @param bossBarStyle
	 *            the new boss bar style
	 */
	public void setBossBarStyle(String bossBarStyle) {
		this.bossBarStyle = bossBarStyle;
	}

	/**
	 * Sets the broadcast msg.
	 *
	 * @param broadcastMsg
	 *            the new broadcast msg
	 */
	public void setBroadcastMsg(String broadcastMsg) {
		this.broadcastMsg = broadcastMsg;
	}

	/**
	 * Sets the chance.
	 *
	 * @param chance
	 *            the new chance
	 */
	public void setChance(double chance) {
		this.chance = chance;
	}

	/**
	 * Sets the choice rewards enabled.
	 *
	 * @param choiceRewardsEnabled
	 *            the new choice rewards enabled
	 */
	public void setChoiceRewardsEnabled(boolean choiceRewardsEnabled) {
		this.choiceRewardsEnabled = choiceRewardsEnabled;
	}

	/**
	 * Sets the choice rewards rewards.
	 *
	 * @param choiceRewardsRewards
	 *            the new choice rewards rewards
	 */
	public void setChoiceRewardsRewards(ArrayList<String> choiceRewardsRewards) {
		this.choiceRewardsRewards = choiceRewardsRewards;
	}

	/**
	 * Sets the console commands.
	 *
	 * @param consoleCommands
	 *            the new console commands
	 */
	public void setConsoleCommands(ArrayList<String> consoleCommands) {
		this.consoleCommands = consoleCommands;
	}

	/**
	 * Sets the delay enabled.
	 *
	 * @param delayEnabled
	 *            the new delay enabled
	 */
	public void setDelayEnabled(boolean delayEnabled) {
		this.delayEnabled = delayEnabled;
	}

	/**
	 * Sets the delay hours.
	 *
	 * @param delayHours
	 *            the new delay hours
	 */
	public void setDelayHours(int delayHours) {
		this.delayHours = delayHours;
	}

	/**
	 * Sets the delay minutes.
	 *
	 * @param delayMinutes
	 *            the new delay minutes
	 */
	public void setDelayMinutes(int delayMinutes) {
		this.delayMinutes = delayMinutes;
	}

	/**
	 * Sets the effect data.
	 *
	 * @param effectData
	 *            the new effect data
	 */
	public void setEffectData(int effectData) {
		this.effectData = effectData;
	}

	/**
	 * Sets the effect effect.
	 *
	 * @param effectEffect
	 *            the new effect effect
	 */
	public void setEffectEffect(String effectEffect) {
		this.effectEffect = effectEffect;
	}

	/**
	 * Sets the effect enabled.
	 *
	 * @param effectEnabled
	 *            the new effect enabled
	 */
	public void setEffectEnabled(boolean effectEnabled) {
		this.effectEnabled = effectEnabled;
	}

	/**
	 * Sets the effect particles.
	 *
	 * @param effectParticles
	 *            the new effect particles
	 */
	public void setEffectParticles(int effectParticles) {
		this.effectParticles = effectParticles;
	}

	/**
	 * Sets the effect radius.
	 *
	 * @param effectRadius
	 *            the new effect radius
	 */
	public void setEffectRadius(int effectRadius) {
		this.effectRadius = effectRadius;
	}

	/**
	 * Sets the exp.
	 *
	 * @param exp
	 *            the new exp
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Sets the firework colors.
	 *
	 * @param fireworkColors
	 *            the new firework colors
	 */
	public void setFireworkColors(ArrayList<String> fireworkColors) {
		this.fireworkColors = fireworkColors;
	}

	/**
	 * Sets the firework enabled.
	 *
	 * @param fireworkEnabled
	 *            the new firework enabled
	 */
	public void setFireworkEnabled(boolean fireworkEnabled) {
		this.fireworkEnabled = fireworkEnabled;
	}

	/**
	 * Sets the firework fade out colors.
	 *
	 * @param fireworkFadeOutColors
	 *            the new firework fade out colors
	 */
	public void setFireworkFadeOutColors(ArrayList<String> fireworkFadeOutColors) {
		this.fireworkFadeOutColors = fireworkFadeOutColors;
	}

	/**
	 * Sets the firework flicker.
	 *
	 * @param fireworkFlicker
	 *            the new firework flicker
	 */
	public void setFireworkFlicker(boolean fireworkFlicker) {
		this.fireworkFlicker = fireworkFlicker;
	}

	/**
	 * Sets the firework power.
	 *
	 * @param fireworkPower
	 *            the new firework power
	 */
	public void setFireworkPower(int fireworkPower) {
		this.fireworkPower = fireworkPower;
	}

	/**
	 * Sets the firework trail.
	 *
	 * @param fireworkTrail
	 *            the new firework trail
	 */
	public void setFireworkTrail(boolean fireworkTrail) {
		this.fireworkTrail = fireworkTrail;
	}

	/**
	 * Sets the firework types.
	 *
	 * @param fireworkTypes
	 *            the new firework types
	 */
	public void setFireworkTypes(ArrayList<String> fireworkTypes) {
		this.fireworkTypes = fireworkTypes;
	}

	/**
	 * Sets the items.
	 *
	 * @param items
	 *            the new items
	 */
	public void setItems(Set<String> items) {
		this.items = items;
	}

	/**
	 * Sets the items and amounts given.
	 *
	 * @param itemsAndAmountsGiven
	 *            the items and amounts given
	 */
	public void setItemsAndAmountsGiven(HashMap<String, Integer> itemsAndAmountsGiven) {
		this.itemsAndAmountsGiven = itemsAndAmountsGiven;
	}

	/**
	 * Sets the javascript enabled.
	 *
	 * @param javascriptEnabled
	 *            the new javascript enabled
	 */
	public void setJavascriptEnabled(boolean javascriptEnabled) {
		this.javascriptEnabled = javascriptEnabled;
	}

	/**
	 * Sets the javascript expression.
	 *
	 * @param javascriptExpression
	 *            the new javascript expression
	 */
	public void setJavascriptExpression(String javascriptExpression) {
		this.javascriptExpression = javascriptExpression;
	}

	/**
	 * Sets the javascript false rewards.
	 *
	 * @param javascriptFalseRewards
	 *            the new javascript false rewards
	 */
	public void setJavascriptFalseRewards(ArrayList<String> javascriptFalseRewards) {
		this.javascriptFalseRewards = javascriptFalseRewards;
	}

	/**
	 * Sets the javascript true rewards.
	 *
	 * @param javascriptTrueRewards
	 *            the new javascript true rewards
	 */
	public void setJavascriptTrueRewards(ArrayList<String> javascriptTrueRewards) {
		this.javascriptTrueRewards = javascriptTrueRewards;
	}

	/**
	 * Sets the max exp.
	 *
	 * @param maxExp
	 *            the new max exp
	 */
	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	/**
	 * Sets the max money.
	 *
	 * @param maxMoney
	 *            the new max money
	 */
	public void setMaxMoney(int maxMoney) {
		MaxMoney = maxMoney;
	}

	/**
	 * Sets the min exp.
	 *
	 * @param minExp
	 *            the new min exp
	 */
	public void setMinExp(int minExp) {
		this.minExp = minExp;
	}

	/**
	 * Sets the min money.
	 *
	 * @param minMoney
	 *            the new min money
	 */
	public void setMinMoney(int minMoney) {
		MinMoney = minMoney;
	}

	/**
	 * Sets the money.
	 *
	 * @param money
	 *            the new money
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Sets the permission.
	 *
	 * @param permission
	 *            the new permission
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * Sets the player commands.
	 *
	 * @param playerCommands
	 *            the new player commands
	 */
	public void setPlayerCommands(ArrayList<String> playerCommands) {
		this.playerCommands = playerCommands;
	}

	/**
	 * Sets the potions.
	 *
	 * @param potions
	 *            the new potions
	 */
	public void setPotions(Set<String> potions) {
		this.potions = potions;
	}

	/**
	 * Sets the potions amplifier.
	 *
	 * @param potionsAmplifier
	 *            the potions amplifier
	 */
	public void setPotionsAmplifier(HashMap<String, Integer> potionsAmplifier) {
		this.potionsAmplifier = potionsAmplifier;
	}

	/**
	 * Sets the potions duration.
	 *
	 * @param potionsDuration
	 *            the potions duration
	 */
	public void setPotionsDuration(HashMap<String, Integer> potionsDuration) {
		this.potionsDuration = potionsDuration;
	}

	/**
	 * Sets the random chance.
	 *
	 * @param randomChance
	 *            the new random chance
	 */
	public void setRandomChance(double randomChance) {
		this.randomChance = randomChance;
	}

	/**
	 * Sets the random fall back.
	 *
	 * @param randomFallBack
	 *            the new random fall back
	 */
	public void setRandomFallBack(ArrayList<String> randomFallBack) {
		this.randomFallBack = randomFallBack;
	}

	/**
	 * Sets the random rewards.
	 *
	 * @param randomRewards
	 *            the new random rewards
	 */
	public void setRandomRewards(ArrayList<String> randomRewards) {
		this.randomRewards = randomRewards;
	}

	/**
	 * Sets the require permission.
	 *
	 * @param requirePermission
	 *            the new require permission
	 */
	public void setRequirePermission(boolean requirePermission) {
		this.requirePermission = requirePermission;
	}

	/**
	 * Sets the reward msg.
	 *
	 * @param rewardMsg
	 *            the new reward msg
	 */
	public void setRewardMsg(String rewardMsg) {
		this.rewardMsg = rewardMsg;
	}

	/**
	 * Sets the reward type.
	 *
	 * @param rewardType
	 *            the new reward type
	 */
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	/**
	 * Sets the sound enabled.
	 *
	 * @param soundEnabled
	 *            the new sound enabled
	 */
	public void setSoundEnabled(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}

	/**
	 * Sets the sound pitch.
	 *
	 * @param soundPitch
	 *            the new sound pitch
	 */
	public void setSoundPitch(float soundPitch) {
		this.soundPitch = soundPitch;
	}

	/**
	 * Sets the sound sound.
	 *
	 * @param soundSound
	 *            the new sound sound
	 */
	public void setSoundSound(String soundSound) {
		this.soundSound = soundSound;
	}

	/**
	 * Sets the sound volume.
	 *
	 * @param soundVolume
	 *            the new sound volume
	 */
	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}

	/**
	 * Sets the timed enabled.
	 *
	 * @param timedEnabled
	 *            the new timed enabled
	 */
	public void setTimedEnabled(boolean timedEnabled) {
		this.timedEnabled = timedEnabled;
	}

	/**
	 * Sets the timed hour.
	 *
	 * @param timedHour
	 *            the new timed hour
	 */
	public void setTimedHour(int timedHour) {
		this.timedHour = timedHour;
	}

	/**
	 * Sets the timed minute.
	 *
	 * @param timedMinute
	 *            the new timed minute
	 */
	public void setTimedMinute(int timedMinute) {
		this.timedMinute = timedMinute;
	}

	/**
	 * Sets the title enabled.
	 *
	 * @param titleEnabled
	 *            the new title enabled
	 */
	public void setTitleEnabled(boolean titleEnabled) {
		this.titleEnabled = titleEnabled;
	}

	/**
	 * Sets the title fade in.
	 *
	 * @param titleFadeIn
	 *            the new title fade in
	 */
	public void setTitleFadeIn(int titleFadeIn) {
		this.titleFadeIn = titleFadeIn;
	}

	/**
	 * Sets the title fade out.
	 *
	 * @param titleFadeOut
	 *            the new title fade out
	 */
	public void setTitleFadeOut(int titleFadeOut) {
		this.titleFadeOut = titleFadeOut;
	}

	/**
	 * Sets the title show time.
	 *
	 * @param titleShowTime
	 *            the new title show time
	 */
	public void setTitleShowTime(int titleShowTime) {
		this.titleShowTime = titleShowTime;
	}

	/**
	 * Sets the title sub title.
	 *
	 * @param titleSubTitle
	 *            the new title sub title
	 */
	public void setTitleSubTitle(String titleSubTitle) {
		this.titleSubTitle = titleSubTitle;
	}

	/**
	 * Sets the title title.
	 *
	 * @param titleTitle
	 *            the new title title
	 */
	public void setTitleTitle(String titleTitle) {
		this.titleTitle = titleTitle;
	}

	/**
	 * Sets the worlds.
	 *
	 * @param worlds
	 *            the new worlds
	 */
	public void setWorlds(ArrayList<String> worlds) {
		this.worlds = worlds;
	}

}
