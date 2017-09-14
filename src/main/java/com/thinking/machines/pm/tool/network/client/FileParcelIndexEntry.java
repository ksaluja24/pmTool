package com.thinking.machines.pm.tool.network.client;
import java.io.*;
public class FileParcelIndexEntry
{
public enum FileType{WORK,DEPENDENCY};
public String fileName;
public FileType fileType;
public long size;
public File file;
public byte [] fileData;
}