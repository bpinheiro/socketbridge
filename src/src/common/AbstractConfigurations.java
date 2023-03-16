package src.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public abstract class AbstractConfigurations {

	//valores alocados na memoria para fácil acesso
	HashMap<String,String> configs = new HashMap<String, String>();
	
	//utilizado para montar o arquivo de configuracoes pela primeira vez
	ArrayList<String[]> configurations = new ArrayList<String[]>();

	private static final char[] CRLF = new char[]{0x0D, 0x0A};
	
	private String description;
	private File configFile;

	public AbstractConfigurations(File dir, String fileName, String description) throws IOException {
		this.configFile = new File(dir, fileName);
		this.description = description;
		
		//pega minhas configuracoes especificas
		fillMyConfigurations();
		
		//Carrega no array
		loadConfiguration();
	}
	
	protected File getConfigFile() {
		return configFile;
	}
	
	protected abstract void fillMyConfigurations();

	/**
	 * Informa as configuracoes atuais
	 * @return
	 */
	public String showConfigurations(){
		//verificando configuracoes
		StringBuffer buffer = new StringBuffer();
		buffer.append(CRLF);
		for (Iterator<Map.Entry<String, String>> it = configs.entrySet().iterator(); it.hasNext();){
				Entry<String,String> configFiel =  it.next();
				buffer.append("[CONFIG] " + configFiel.getKey() + " = " + configFiel.getValue());
				buffer.append(CRLF);
		}
		return buffer.toString();
	}

	
	public void setConfiguration(String key, String value){
		configs.put(key, value);
	}
	
	public void setConfiguration(String key, File file){
		String name = file.getAbsolutePath().replace('\\','/');
		configs.put(key,name);
	}

	public void initConfiguration(String key, String value, String comment){
		configurations.add(new String[]{key,value,comment});
	}

	public String getStringConfig(String key){
		return configs.get(key);
	}
	
	public boolean getBoolConfig(String key){
		return Boolean.parseBoolean(configs.get(key));
	}

	public long getLongConfig(String key){
		return Long.parseLong(configs.get(key));
	}
	
	public int getIntConfig(String key){
		return Integer.parseInt(configs.get(key));
	}
	
	/**
	 * Carrega as configurações em ram
	 * @throws IOException
	 */
	private void  loadConfiguration() throws IOException{
		if(fileExist()){
			Properties prop  = new Properties();
			prop.load(new FileInputStream(configFile));

			for (String[] config : configurations) {
				String param = config[0];
				String value = config[1];
				configs.put(param,prop.getProperty(param,value));
			}
		} else {
			saveConfiguration();
		}
		
	}
	
	
	/**
	 * Salva todas as configurações
	 * @throws IOException
	 */
	public void saveConfiguration() throws IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("#config file");
		buffer.append(CRLF);

		if(description.length() > 0){
			buffer.append("#" + description);
			buffer.append(CRLF);
		}
		buffer.append(CRLF);
		
		for (String[] config : configurations) {
			
			String param = config[0];
			String value = config[1];
			String comment = config[2];
			
			buffer.append("#" + comment);
			buffer.append(CRLF);
			buffer.append(param);
			buffer.append('=');
			
			if(configs.containsKey(param)){
				buffer.append(configs.get(param));
			}else{
				buffer.append(value);
				configs.put(param, value);
			}
			
			buffer.append(CRLF);
			buffer.append(CRLF);
		}
		
		FileOutputStream out = new FileOutputStream(configFile);
		out.write(buffer.toString().getBytes());
		out.close();
	}

	/**
	 * Verifica se o arquivo existe, e retorna o status
	 * se nao existir já cria
	 * @return
	 * @throws IOException
	 */
	private  boolean fileExist() throws IOException {
		if (!configFile.exists()){
			configFile.createNewFile();
			return false;
		} else {
			return true;
		}
	}
}
