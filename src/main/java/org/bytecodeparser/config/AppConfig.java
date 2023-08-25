package org.bytecodeparser.config;

import lombok.Getter;

@Getter
public class AppConfig {

    private String fileName;
    private String filePath;
    private boolean local;

    public static AppConfig resolve(String[] args) {
        AppConfig appConfig = new AppConfig();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-fn") || args[i].equals("--filename")) {
                appConfig.fileName = args[i + 1];
                i++;
                continue;
            }
            if (args[i].equals("-fp") || args[i].equals("--filepath")) {
                appConfig.filePath = args[i + 1];
                i++;
                continue;
            }
            if (args[i].equals("-l") || args[i].equals("--local")) {
                appConfig.local = true;
            }
        }

        appConfig.validate();

        return appConfig;
    }

    private void validate() {
        if (this.fileName == null && this.filePath == null) {
            throw new IllegalArgumentException("You must provide filename OR filepath");
        }
        if (this.fileName != null && this.filePath != null) {
            throw new IllegalArgumentException("You can't provide both filename AND filepath");
        }
    }
}
