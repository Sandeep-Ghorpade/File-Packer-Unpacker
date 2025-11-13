////////////////////////////////////////////////////////////////////////////////////
//
// Function Name : Marvellous Packer Unpacker Activity
// Description   : Combines file packing and unpacking operations in one application
// Input         : Directory name (for packing) or packed file name (for unpacking)
// Author        : Sandeep Sanjay Ghorpade
// Date          : 25/07/2025
//
////////////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.*;

class MarvellousPacker
{
    private String DirectoryName;
    private String PackFileName;

    public MarvellousPacker(String dir, String packName)
    {
        this.DirectoryName = dir;
        this.PackFileName = packName;
    }

    public void PackingActivity()
    {
        File dirObj = new File(DirectoryName);
        FileOutputStream outStream = null;
        int iCountFile = 0;

        try
        {
            System.out.println("--------------------------------------------------------");
            System.out.println("----------- Marvellous Packer Unpacker -----------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("------------------- Packing Activity -------------------");
            System.out.println("--------------------------------------------------------");

            if(!dirObj.exists() || !dirObj.isDirectory())
            {
                System.out.println("Invalid Directory Name.");
                return;
            }

            File packObj = new File(PackFileName);
            packObj.createNewFile();
            outStream = new FileOutputStream(packObj);

            File files[] = dirObj.listFiles();

            System.out.println("Scanning directory: " + DirectoryName);
            System.out.println("--------------------------------------------------------");

            for(File file : files)
            {
                if(file.isFile())
                {
                    String Header = file.getName() + " " + file.length();
                    
                    for(int i = Header.length(); i < 100; i++)
                    {
                        Header += " ";
                    }

                    byte headerBytes[] = Header.getBytes();
                    outStream.write(headerBytes, 0, headerBytes.length);

                    FileInputStream inStream = new FileInputStream(file);
                    byte buffer[] = new byte[(int)file.length()];
                    inStream.read(buffer, 0, buffer.length);
                    outStream.write(buffer, 0, buffer.length);
                    inStream.close();

                    System.out.println("Packed file : " + file.getName() + " (" + file.length() + " bytes)");
                    iCountFile++;
                }
            }

            System.out.println("--------------------------------------------------------");
            System.out.println("------------------ Statistical Report ------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("Total files packed : " + iCountFile);
            System.out.println("Packed file created : " + PackFileName);
            System.out.println("--------------------------------------------------------");
            System.out.println("--------- Thank you for using our application ----------");
            System.out.println("--------------------------------------------------------");

            outStream.close();
        }
        catch(Exception e)
        {
            System.out.println("Error during packing: " + e);
        }
    }
}

class MarvellousUnpacker
{
    private String PackName;

    public MarvellousUnpacker(String A)
    {
        this.PackName = A;
    }

    public void UnpackingActivity()
    {
        try
        {
            System.out.println("--------------------------------------------------------");
            System.out.println("----------- Marvellous Packer Unpacker -----------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("----------------- UnPacking Activity -------------------");
            System.out.println("--------------------------------------------------------");

            File fobj = new File(PackName);

            if(!fobj.exists())
            {
                System.out.println("Unable to access Packed file");
                return;
            }

            System.out.println("Packed file successfully opened.");

            FileInputStream fiobj = new FileInputStream(fobj);
            byte HeaderBuffer[] = new byte[100];
            int iRet = 0, iCountFile = 0;

            while((iRet = fiobj.read(HeaderBuffer, 0, 100)) != -1)
            {
                String Header = new String(HeaderBuffer).trim();
                if(Header.isEmpty()) break;

                String Tokens[] = Header.split(" ");
                String FileName = Tokens[0];
                int FileSize = Integer.parseInt(Tokens[1]);

                byte Buffer[] = new byte[FileSize];
                fiobj.read(Buffer, 0, FileSize);

                File newFile = new File(FileName);
                FileOutputStream foobj = new FileOutputStream(newFile);
                foobj.write(Buffer, 0, FileSize);
                foobj.close();

                System.out.println("Unpacked file : " + FileName + " (" + FileSize + " bytes)");
                iCountFile++;
            }

            System.out.println("--------------------------------------------------------");
            System.out.println("------------------ Statistical Report ------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("Total files unpacked : " + iCountFile);
            System.out.println("--------------------------------------------------------");
            System.out.println("--------- Thank you for using our application ----------");
            System.out.println("--------------------------------------------------------");

            fiobj.close();
        }
        catch(Exception e)
        {
            System.out.println("Error during unpacking: " + e);
        }
    }
}

public class MarvellousPackerUnpacker
{
    public static void main(String A[])
    {
        Scanner sobj = new Scanner(System.in);

        try
        {
            System.out.println("--------------------------------------------------------");
            System.out.println("----------- Marvellous Packer Unpacker -----------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("1 : Pack files from directory");
            System.out.println("2 : Unpack files from packed file");
            System.out.println("--------------------------------------------------------");
            System.out.print("Enter your choice : ");

            int choice = sobj.nextInt();
            sobj.nextLine(); // clear buffer

            switch(choice)
            {
                case 1:
                    System.out.print("Enter directory name to pack : ");
                    String dirName = sobj.nextLine();

                    System.out.print("Enter name for packed file : ");
                    String packedFile = sobj.nextLine();

                    MarvellousPacker pobj = new MarvellousPacker(dirName, packedFile);
                    pobj.PackingActivity();
                    break;

                case 2:
                    System.out.print("Enter the name of packed file to unpack : ");
                    String packName = sobj.nextLine();

                    MarvellousUnpacker uobj = new MarvellousUnpacker(packName);
                    uobj.UnpackingActivity();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }
        finally
        {
            sobj.close();
        }
    }
}
