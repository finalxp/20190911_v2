package com.validsoft.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.validsoft.utils.AudioUtils;
import com.validsoft.utils.FileUtils;

public class swing extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame jFrame;
	private static JLabel jLabel_fileOpen;
	private static JTextField jTextField_fileDirectory;
	private static JButton jButton_fileOpen;
	private static JButton jButton_start_toBase64;
	private static JButton jButton_start_toAudio;
	private static List<String> files;
	private String string_file;

	public void demo() {
		this.string_file = new String();

		jLabel_fileOpen = new JLabel("文件选择:");
		jTextField_fileDirectory = new JTextField();
		jTextField_fileDirectory.setPreferredSize(new Dimension(200, 30));
		jButton_fileOpen = new JButton("打开目录");
		jButton_fileOpen.addMouseListener(this);

		jButton_start_toBase64 = new JButton("Audio >>> Base64");
		jButton_start_toBase64.setBackground(Color.CYAN);
		jButton_start_toBase64.addMouseListener(this);

		jButton_start_toAudio = new JButton("Base64 >>> Audio");
		jButton_start_toAudio.setBackground(Color.GREEN);
		jButton_start_toAudio.addMouseListener(this);

		JPanel jPanel = new JPanel();
		jPanel.add(jLabel_fileOpen);
		jPanel.add(jTextField_fileDirectory);
		jPanel.add(jButton_fileOpen);
		add(jPanel);

		JPanel jPanel3 = new JPanel();
		jPanel3.add(jButton_start_toAudio);
		jPanel3.add(jButton_start_toBase64);
		add(jPanel3);

		setLayout(new FlowLayout(1));
		setTitle("Audio Base64 interturn tool");
		setSize(450, 150);
		setVisible(true);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(jButton_fileOpen)) {
			doFileChoose();
		} else if (e.getSource().equals(jButton_start_toBase64)) {
			doStart2Base64();
		} else if (e.getSource().equals(jButton_start_toAudio)) {
			doStart2Audio();
		}
	}

	private void doFileChoose() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fileChooser.showDialog(jFrame, "确定");
		System.out.println(result);
		if (result == 0) {
			jTextField_fileDirectory.setText(fileChooser.getSelectedFile()
					.getAbsolutePath());

			files = FileUtils
					.getFiles(jTextField_fileDirectory.getText(), true);

			System.out.println("file -->" + files);
			for (String string : files) {
				System.out.println("file -->" + string);
			}
		}
	}

	private void doStart2Base64() {
		for (String string : files) {
			System.out.println(string);
			byte[] readAudioData = AudioUtils.readAudioData(string);
			String fileNameWithOutExt = FileUtils.getFileNameWithOutExt(string);
			String finalPath = jTextField_fileDirectory.getText() + "\\"
					+ fileNameWithOutExt + ".txt";
			AudioUtils.saveToFile(finalPath,
					AudioUtils.byteArrayToBase64(readAudioData));
		}
	}

	private void doStart2Audio() {
		for (String string : files) {
			System.out.println(string);
			File file = new File(string);
			String readFile = AudioUtils.readFile(file);
			byte[] base64ToByteArray = AudioUtils.base64ToByteArray(readFile);
			String fileNameWithOutExt = FileUtils.getFileNameWithOutExt(string);
			String finalPath = jTextField_fileDirectory.getText() + "\\"
					+ fileNameWithOutExt + ".pcm";
			AudioUtils.saveBinaryFile(finalPath, base64ToByteArray);

		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
