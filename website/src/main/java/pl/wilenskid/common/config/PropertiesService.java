package pl.wilenskid.common.config;

import lombok.Getter;

import javax.inject.Named;

@Getter
@Named
public class PropertiesService {

  private final String location = "upload-dir";

}
