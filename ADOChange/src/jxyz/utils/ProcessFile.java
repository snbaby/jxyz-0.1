package jxyz.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProcessFile {
	
	public static void main(String[] args) throws Exception
	{
		File file = new File("D:\\项目\\软通ADO项目\\备份\\20201125\\dm.sql");
		change(file);
		
		File file2 = new File("D:\\项目\\软通ADO项目\\备份\\20201125\\dwr.sql");
		change(file2);
	}
	
	
	public static void change(File file) throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		List<String> content = new ArrayList<String>();
		String str = null;
		int state = 0;
		while( (str = reader.readLine()) != null)
		{
			if(str.startsWith("LOCK TABLES"))
			{
				state = 1;
				continue;
			}
			else if(str.startsWith("UNLOCK TABLES"))
			{
				state = 0;
				content.add("");
				content.add("");
				continue;
			}
			
			if(state == 1)
			{
				content.add(str);
			}
		}
		reader.close();
		
		String dir = file.getCanonicalPath();
		String name = file.getParent() + "\\" + file.getName().substring(0, file.getName().lastIndexOf(".")) + "_out.sql";
		File out = new File(name);
		PrintWriter pw = new PrintWriter(out, "UTF-8");
		for(String str1 : content)
		{
			pw.println(str1);
		}
		pw.close();
	}

}
