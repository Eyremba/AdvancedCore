package com.Ben12345rocks.AdvancedCore.Report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.Util.Files.FilesManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Report.
 */
public class Report {

	/** The instance. */
	static Report instance = new Report();

	/**
	 * Gets the single instance of Report.
	 *
	 * @return single instance of Report
	 */
	public static Report getInstance() {
		return instance;
	}

	/** The plugin. */
	AdvancedCoreHook plugin = AdvancedCoreHook.getInstance();

	/** The data. */
	FileConfiguration data;

	/** The d file. */
	File dFile;

	/**
	 * Instantiates a new report.
	 */
	private Report() {
	}

	/**
	 * Adds the all files.
	 *
	 * @param dir
	 *            the dir
	 * @param fileList
	 *            the file list
	 */
	public void addAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {

					plugin.debug("directory:" + file.getCanonicalPath());

					addAllFiles(file, fileList);
				} else {

					plugin.debug("file:" + file.getCanonicalPath());

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the to zip.
	 *
	 * @param file
	 *            the file
	 * @param zos
	 *            the zos
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void addToZip(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		String zipFilePath = file.getPath();

		plugin.debug("Writing '" + zipFilePath + "' to zip file");

		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	/**
	 * Creates the.
	 */
	public void create() {
		long time = Calendar.getInstance().getTime().getTime();
		String zipFileName = "Reports." + Long.toString(time);
		List<File> fileList = new ArrayList<File>();
		ArrayList<Plugin> plugins = new ArrayList<Plugin>();
		plugins.add(plugin.getPlugin());
		for (Plugin plugin : plugins) {
			File directoryToZip = plugin.getDataFolder();

			try {
				this.plugin.getPlugin().getLogger()
						.info("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			addAllFiles(directoryToZip, fileList);
		}
		plugin.getPlugin().getLogger().info("---Creating zip file");
		writeZipFile(fileList, zipFileName);
		plugin.getPlugin().getLogger().info("---Done");
	}

	/**
	 * Creates the.
	 *
	 * @param directory
	 *            the directory
	 * @param zipFileName
	 *            the zip file name
	 */
	public void create(File directory, String zipFileName) {
		File directoryToZip = directory;

		List<File> fileList = new ArrayList<File>();
		try {
			plugin.getPlugin().getLogger()
					.info("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		addAllFiles(directoryToZip, fileList);
		plugin.getPlugin().getLogger().info("---Creating zip file");
		writeZipFile(fileList, zipFileName);
		plugin.getPlugin().getLogger().info("---Done");
	}

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
	 * Write zip file.
	 *
	 * @param fileList
	 *            the file list
	 * @param zipFileName
	 *            the zip file name
	 */
	public void writeZipFile(List<File> fileList, String zipFileName) {

		try {
			File fileZipFolder = new File(
					plugin.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "Reports");
			if (!fileZipFolder.exists()) {
				fileZipFolder.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(plugin.getPlugin().getDataFolder().getAbsolutePath()
					+ File.separator + "Reports" + File.separator + zipFileName + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
