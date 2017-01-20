package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class BuildIndex {
	public static void main(String[] args) {
		File dir = new File("D:\\Sohumirror");
		File indexDir = new File("D:\\indexTest");
		IndexWriter indexWriter = null;
		long startTime = 0l;
		try {
			Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
			Directory directory = new SimpleFSDirectory(indexDir);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,
					luceneAnalyzer);
			indexWriter = new IndexWriter(directory, config);
			indexDocs(indexWriter,dir);
			/*File[] files = dir.listFiles();
			startTime = new Date().getTime();
			FileInputStream in;
			for (int i = 0 ; i<files.length;i++) {
				 
					System.out.println("文件 " + files[i].getCanonicalPath()
							+ " 开始索引");
					
					FileReader reader = new FileReader(files[i]);
					in = new FileInputStream(files[i]);
					Document document = new Document();
					document.add(new Field("title", files[i].getName(),
							Field.Store.YES, Field.Index.ANALYZED));
					System.out.println(files[i].getName());
					document.add(new Field("content", new InputStreamReader(in, StandardCharsets.UTF_8)));//new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))));

					System.out.println(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
					document.add(new Field("url", files[i].getPath(),
							Field.Store.YES, Field.Index.ANALYZED));
					System.out.println(files[i].getPath());
					indexWriter.addDocument(document);
				in.close();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		long endTime = new Date().getTime();
		System.out.println("用时：" + (endTime - startTime) + " 毫秒");
		System.out.println("索引路径：" + indexDir.getPath());
	}
	
	
	
	
	static void indexDocs(IndexWriter writer, File file)
		    throws IOException {
		    // do not try to index files that cannot be read
		    if (file.canRead()) {
		      if (file.isDirectory()) {
		        String[] files = file.list();
		        // an IO error could occur
		        if (files != null) {
		          for (int i = 0; i < files.length; i++) {
		            indexDocs(writer, new File(file, files[i]));
		          }
		        }
		      }
		      else {
		    	    long startTime = new Date().getTime();
					FileInputStream in;
					System.out.println("文件 " + file.getCanonicalPath()
							+ " 开始索引");
					
					FileReader reader = new FileReader(file);
					in = new FileInputStream(file);
					Document document = new Document();
					document.add(new Field("title", file.getName(),
							Field.Store.YES, Field.Index.ANALYZED));
					System.out.println(file.getName());
					document.add(new Field("content", new InputStreamReader(in, StandardCharsets.UTF_8)));//new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))));

					System.out.println(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
					document.add(new Field("url", file.getPath(),
							Field.Store.YES, Field.Index.ANALYZED));
					System.out.println(file.getPath());
					writer.addDocument(document);					
					in.close();


		      }
		    }
		  }
	
	

}
