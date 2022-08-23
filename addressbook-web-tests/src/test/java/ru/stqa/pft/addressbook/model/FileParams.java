package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class FileParams {

  private String format;
  private String fileName;
  private String dir;

  public FileParams() {
  }

  public String getFormat() {
    return format;
  }

  public String getName() {
    return fileName;
  }

  public FileParams withFormat(String format) {
    this.format = format;
    return this;
  }

  public FileParams withName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public FileParams inFileDir(String dir) {
    this.dir = dir;
    return this;
  }

  public String getDir() {
    return dir;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileParams fileParams = (FileParams) o;
    return Objects.equals(format, fileParams.format) &&
            Objects.equals(fileName, fileParams.fileName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(format, fileName);
  }

  @Override
  public String toString() {
    return "FilesData{" +
            "format='" + format + '\'' +
            ", fileName='" + fileName + '\'' +
            '}';
  }



}
