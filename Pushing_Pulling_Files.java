package com.amazonaws.samples;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;

public class Pushing_Pulling_Files {
	public static void main(String[] args) throws IOException {
		
		AWSCredentials mycredentials = null;
		try {
			 mycredentials = new ProfileCredentialsProvider("default").getCredentials();
	} catch(Exception e) {
		throw new AmazonClientException(
                "Please make sure that your credentials file is at the correct and is in valid format",
                e);
	} 
				AmazonS3 mys3 = AmazonS3ClientBuilder.standard().
						withRegion(Regions.US_EAST_2).
						withCredentials(new AWSStaticCredentialsProvider(mycredentials)).
						build();
	
	String bucketName = "mys3javabckt";
	String key = "File_To_Upload";
	String File_loc = "C:\\Users\\Vimoka\\Desktop\\File_To_Upload.txt";
	
	try {
		//Creating bucket
		System.out.println("Creating bucket");
		mys3.createBucket(bucketName);

		//uploading files into the bucket
		System.out.println("Uploading files to bucket");
		mys3.putObject(bucketName, key, File_loc );
		
		//downloading files into local machine
		System.out.println("Downloading an object");
        com.amazonaws.services.s3.model.S3Object object = mys3.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
        displayInputStream(object.getObjectContent());
		
	}catch(AmazonServiceException f) {
		System.out.println("Error Occured :" +f.getMessage());
	}catch(AmazonClientException c) {
		System.out.println("Error Occured :" +c.getMessage());
	}
}
	private static void displayInputStream(InputStream input) throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = buff.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
	}


