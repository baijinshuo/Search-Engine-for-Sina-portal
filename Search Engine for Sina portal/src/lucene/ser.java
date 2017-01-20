package lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Servlet implementation class ser
 */
@WebServlet("/ser")
public class ser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] fields = {"name","content"};  
        
        //创建一个分词器,和创建索引时用的分词器要一致  
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);  
           
        //创建查询解析器  
        QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_36,fields,analyzer);  
           
        //将查询关键词解析成Lucene的Query对象  
        Query query=null;
		try {
			query = queryParser.parse("s");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
           
        //打开索引目录  
        File indexDir = new File("D:\\indexTest");  
        Directory directory = FSDirectory.open(indexDir);  
           
        //获取访问索引的接口,进行搜索  
        IndexReader indexReader  = IndexReader.open(directory);  
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);  
           
        //TopDocs 搜索返回的结果  
        TopDocs topDocs = indexSearcher.search(query, 100);//只返回前100条记录  
           
        int totalCount = topDocs.totalHits; // 搜索结果总数量  
        System.out.println("搜索到的结果总数量为：" + totalCount);  
           
        ScoreDoc[] scoreDocs = topDocs.scoreDocs; // 搜索的结果集合  
           
        List<Document> docs = new ArrayList<Document>();  
           
        for(ScoreDoc scoreDoc : scoreDocs) {  
            //文档编号  
            int docID = scoreDoc.doc;  
            //根据文档编号获取文档  
            Document doc = indexSearcher.doc(docID);  
            System.out.println(doc);//docs.add(doc);  
        }  
        indexReader.close();  
        indexSearcher.close();  
	}

}
