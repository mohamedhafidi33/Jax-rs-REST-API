package ma.shm.hassani.entities;


import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@XmlRootElement
public class Link {
	private String rel;
	private String url;
}
