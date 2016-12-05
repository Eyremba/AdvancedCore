package com.Ben12345rocks.AdvancedCore.YML;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Ben12345rocks.AdvancedCore.Util.Files.FilesManager;

/**
 * The Class YMLFile.
 */
public abstract class YMLFile {
	
	/**
	 * Gets the d file.
	 *
	 * @return the d file
	 */
	public File getdFile() {
		return dFile;
	}

	/**
	 * Instantiates a new YML file.
	 *
	 * @param file
	 *            the file
	 * @param setup
	 *            the setup
	 */
	public YMLFile(File file, boolean setup) {
		dFile = file;
		if (setup) {
			setup();
		}
	}

	/**
	 * Instantiates a new YML file.
	 *
	 * @param file
	 *            the file
	 */
	public YMLFile(File file) {
		dFile = file;
	}

	/** The data. */
	private FileConfiguration data;

	/** The d file. */
	private File dFile;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public FileConfiguration getData() {
		return data;
	}

	/**
	 * Reload data.
	 */
	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(dFile);
	}

	/**
	 * Save data.
	 */
	public void saveData() {

		FilesManager.getInstance().editFile(dFile, data);

	}

	/**
	 * On file creation.
	 */
	public abstract void onFileCreation();

	/**
	 * Setup.
	 */
	public void setup() {
		getdFile().getParentFile().mkdirs();

		if (!dFile.exists()) {
			try {
				getdFile().createNewFile();
				onFileCreation();
			} catch (IOException e) {
				Bukkit.getServer()
						.getLogger()
						.severe(ChatColor.RED + "Could not create "
								+ getdFile().getName() + "!");
			}
		}

		data = YamlConfiguration.loadConfiguration(dFile);
	}
}