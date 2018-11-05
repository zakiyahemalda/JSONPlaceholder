

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JLabel;
import javax.swing.JScrollBar;

public class lab7 {

	private JFrame frame;
	private JTextField textFieldUserId;
	private JTextField textFieldTitle;
	private JTextField textFieldComplete;
	private JTextField textFieldId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lab7 window = new lab7();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public lab7() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 349);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(20, 39, 493, 110);
		frame.getContentPane().add(textArea);
		
		JButton btnGetDataFrom = new JButton("Get data from JSON");
		btnGetDataFrom.setBounds(170, 11, 190, 23);
		frame.getContentPane().add(btnGetDataFrom);
		btnGetDataFrom.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread (new Runnable()
				{
					public void run()
					{
						
						String params = null;
						String strUrl = "https://jsonplaceholder.typicode.com/todos";
						JSONArray jsonObj;
						try {
							jsonObj = new JSONArray(makeHttpRequest(strUrl,"GET", params)) ;
							String strFromPHP = null;
							strFromPHP = jsonObj.toString();
							textArea.setText(strFromPHP);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
				});
				thread.start();
	           
			}
		});
		
		textFieldUserId = new JTextField();
		textFieldUserId.setBounds(76, 174, 77, 20);
		frame.getContentPane().add(textFieldUserId);
		textFieldUserId.setColumns(10);
		
		
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(407, 39, 17, 93);
		frame.getContentPane().add(scrollBar);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setEditable(false);
		textFieldTitle.setBounds(274, 174, 239, 20);
		frame.getContentPane().add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		textFieldComplete = new JTextField();
		textFieldComplete.setEditable(false);
		textFieldComplete.setBounds(274, 218, 101, 20);
		frame.getContentPane().add(textFieldComplete);
		textFieldComplete.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(207, 177, 77, 14);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblUserid = new JLabel("User ID:");
		lblUserid.setBounds(20, 177, 46, 14);
		frame.getContentPane().add(lblUserid);
		
		JLabel lblToDoId = new JLabel("ID:");
		lblToDoId.setBounds(20, 221, 46, 14);
		frame.getContentPane().add(lblToDoId);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(76, 218, 77, 20);
		frame.getContentPane().add(textFieldId);
		textFieldId.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(64, 259, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JLabel lblCompleted = new JLabel("Completed:");
		lblCompleted.setBounds(207, 221, 77, 14);
		frame.getContentPane().add(lblCompleted);
		btnSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread (new Runnable()
				{
					public void run()
					{
						String value1 = textFieldUserId.getText();
						String value2 = textFieldId.getText();
						String params = "userId="+value1+"&id="+value2;
						String strUrl = "https://jsonplaceholder.typicode.com/todos";
						JSONArray jsonObj;
						
						try {
							jsonObj = new JSONArray(makeHttpRequest(strUrl,"GET", params)) ;
							 
							JSONObject obj1 = jsonObj.getJSONObject(0);
							String title = obj1.getString("title");
							String completed = obj1.get("completed").toString();
							textFieldTitle.setText(title);
							textFieldComplete.setText(completed);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
				});
				thread.start();
	           
			}
		});
	}
	
	public String makeHttpRequest(String strUrl, String method, String params) throws JSONException {
		InputStream is = null;
		String json = "";
		
		try {
			strUrl = strUrl+"?"+params;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine())!=null) 
				sb.append(line+"\n");
			is.close();
			json = sb.toString();
			
		}	catch (Exception ee) {
			ee.printStackTrace();
		}
		return json;
	}
}