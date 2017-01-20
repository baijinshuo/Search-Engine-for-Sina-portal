package lucene;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public search() {
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
		response.setContentType("txt/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		String str = request.getParameter("queryString");
		System.out.println(str);
		System.out.println(request.getContentType());
		String queryString = new String(request.getParameter("queryString").getBytes("ISO-8859-1"),"UTF-8");
		//str = new String(b);
		out.println(queryString);
		System.out.println(queryString);
		//request.setCharacterEncoding("UTF-8");
		//String queryString = request.getParameter("queryString");
		String indexPath = "D:\\indexTest";	
	    
		Directory directory = FSDirectory.open(new File(indexPath));
		String[] q ={queryString};//,"catalog","list","llist","download","att"}; 
		String[] fields={"content"};//,"title","url","url","url","url","url"};
		Occur[] occ={Occur.MUST};//,Occur.MUST_NOT,Occur.MUST_NOT,Occur.MUST_NOT,Occur.MUST_NOT,Occur.MUST_NOT};
		IndexReader reader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		//Term t = new Term("title", queryString);
       // TermQuery query = new TermQuery(t);  
		Query query = null;
		try {
			query =  MultiFieldQueryParser.parse(Version.LUCENE_36, q, fields, occ, analyzer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreDoc[] hits = searcher.search(query,100000).scoreDocs;
		
		if (hits.length == 0){
			System.out.println("No file found!");
		}
		for(int i = 0;i<hits.length;i++){
			
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("url");//reader.document(hits[i].doc).get("content");
			String content = doc.get("content");//reader.document(hits[i].doc).get("title");
			String title = doc.get("title");//reader.document(hits[i].doc).get("url");
			//System.out.println(i + path + "\n" + title + "\n" + content );
			System.out.println(i + path);
			System.out.println(title);
			System.out.println(content);
		}
			
		
		System.out.println(queryString);//写入文档数目
		System.out.println(hits.length);
		searcher.close();
		//response.sendRedirect("MyFile/cufeIndex.html");
		//response.sendRedirect("ResultPage.html");
		int len=hits.length;
		//System.out.println(len);
		
		
		int j = 1;
		
	}

}
