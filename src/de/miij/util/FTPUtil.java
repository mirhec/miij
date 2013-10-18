package de.miij.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import sun.net.ftp.FtpClient;

public class FTPUtil
{
	public static int BUFFER_SIZE = 10240;

	public static void putFile(String filename, FtpClient m_client)
	{
		byte[] buffer = new byte[BUFFER_SIZE];
		try
		{
			FileInputStream in = new FileInputStream(filename);
			OutputStream out = m_client.putFileStream(new File(filename).getName());

			int counter = 0;
			while (true)
			{
				int bytes = in.read(buffer);
				if (bytes < 0)
					break;
				out.write(buffer, 0, bytes);
				counter += bytes;
			}

			out.close();
			in.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
