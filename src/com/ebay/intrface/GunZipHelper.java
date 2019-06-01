package com.ebay.intrface;

import java.io.IOException;

public interface GunZipHelper {
	void extractAllGunZipFiles(String inputFilePath,String extractFilesIntoFolder) throws IOException;
}
