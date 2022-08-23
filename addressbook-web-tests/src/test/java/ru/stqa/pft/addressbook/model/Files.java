package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class Files extends ForwardingSet<FileParams> {

  private Set<FileParams> delegate;

  public Files(Files files) {
    this.delegate = new HashSet<FileParams>(files.delegate);
  }

  public Files() {
    this.delegate = new HashSet<FileParams>();
  }

  @Override
  protected Set<FileParams> delegate() {
    return delegate;
  }
}
