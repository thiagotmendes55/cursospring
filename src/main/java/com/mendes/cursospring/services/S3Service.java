package com.mendes.cursospring.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	private Logger logger = LoggerFactory.getLogger(S3Service.class.getName());
	
	@Autowired
	public AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uploadFile(String localFilePath) {
		try {
			File file = new File(localFilePath);
			logger.info("Iniciando upload");
			s3client.putObject(new PutObjectRequest(bucketName, "teste.png", file));
			logger.info("Finalizado o upload");
		} catch (AmazonServiceException e) {
			logger.info("S3 service exception" + e.getMessage());
			logger.info("Status code: " + e.getErrorCode());
		} catch (AmazonClientException e) {
			logger.info("S3 service exception" + e.getMessage());
		}
	}
}
