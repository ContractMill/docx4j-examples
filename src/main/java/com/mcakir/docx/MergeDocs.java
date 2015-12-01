package com.mcakir.docx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

public class MergeDocs {
	
	private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";  

	public static boolean merge(final List<String> paths, String outputDoc) {
		
		try {
			
			WordprocessingMLPackage target = null;  
		    final File merged = new File(outputDoc);
		  
		    InputStream is = null;
		    
		    int chunkId = 0;  
		    for(String path : paths) {
		    	
		    	is = new FileInputStream(path);
		    	if(is != null){
		    		
		    		if(target == null){
		    			
		    			OutputStream os = new FileOutputStream(merged);  
		  	          	os.write(IOUtils.toByteArray(is));  
		  	          	os.close(); 
		  	          	
		  	          	target = WordprocessingMLPackage.load(merged); 
		    			
		    		} else {
		    			
		    			MainDocumentPart main = target.getMainDocumentPart();
		    			AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(new PartName("/part" + chunkId++ + ".docx"));  
		    			afiPart.setContentType(new ContentType(CONTENT_TYPE));  
		    			afiPart.setBinaryData(IOUtils.toByteArray(is));  
		    			Relationship altChunkRel = main.addTargetPart(afiPart);  
		    	  
		    			CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();  
		    			chunk.setId(altChunkRel.getId());  
		    	  
		    			main.addObject(chunk);
		    		}

	    			IOUtils.closeQuietly(is);
		    	}
		    	
		    }
		    
		    if (target != null) {  
		    	
		    	target.save(merged);  
		    	return true;
		    	
		    } 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return false;
	}
	
}
