package com.jackson.p2padmin2022.creditor.fdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * FastDFS文件上传
 * 
 * @author yanglijun
 *
 */
public class FastdfsClient {

	/**
	 * FastDFS上传文件
	 *
	 * @param localFile
	 * @param fileExtendsName
	 * @return
	 */
	public static String[] uploadFileToFastdfs (String localFile, String fileExtendsName) {
		TrackerClient trackerClient = null;
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		StorageClient storageClient = null;
		String[] arry = null;
		try {
			//使用fastdfs上传文件
			ClientGlobal.init("fastdfs_client.conf");

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getTrackerServer();
			storageServer = trackerClient.getStoreStorage(trackerServer);

			storageClient = new StorageClient(trackerServer, storageServer);
			//文件上传
			arry = storageClient.upload_file(localFile, fileExtendsName, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return arry;
	}

	public static void deleteFileFromFastdfs (String groupName, String remoteFileName) {
		TrackerClient trackerClient = null;
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		StorageClient storageClient = null;
		String[] arry = null;
		try {
			//使用fastdfs上传文件
			ClientGlobal.init("fastdfs_client.conf");

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getTrackerServer();
			storageServer = trackerClient.getStoreStorage(trackerServer);

			storageClient = new StorageClient(trackerServer, storageServer);
			//文件上传
			storageClient.delete_file(groupName, remoteFileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
}