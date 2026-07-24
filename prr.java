import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javax.imageio.ImageIO;
import java.io.File;

public class HallTicketGenerator extends JFrame {
    JTextField nameField, rollField, examField;
    JButton generateBtn;
    JLabel qrLabel;

    public HallTicketGenerator() {
        setTitle("Hall Ticket Generator with QR Code");
        setSize(400, 500);
        setLayout(new FlowLayout());

        add(new JLabel("Name:"));
        nameField = new JTextField(30);
        add(nameField);

        add(new JLabel("Roll No:"));
        rollField = new JTextField(30);
        add(rollField);

        add(new JLabel("Exam Name:"));
        examField = new JTextField(30);
        add(examField);

        generateBtn = new JButton("Generate Hall Ticket");
        add(generateBtn);

        qrLabel = new JLabel();
        add(qrLabel);

        generateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Name: " + nameField.getText()
                        + "\nRoll No: " + rollField.getText()
                        + "\nExam: " + examField.getText();

                try {
                    BufferedImage qrImage = generateQRCodeImage(data, 200, 200);
                    qrLabel.setIcon(new ImageIcon(qrImage));
                    // Save QR code image
                    ImageIO.write(qrImage, "png", new File("hall_ticket_qr.png"));
                    JOptionPane.showMessageDialog(null, "Hall ticket generated and QR code saved!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to generate QR code.");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static BufferedImage generateQRCodeImage(String data, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return image;
    }

    public static void main(String[] args) {
        new HallTicketGenerator();
    }
}