package structs;

/**
 * 
 * Base interface for defining the methods necessary for serializing/deserializing a Project.
 * @author cesar.reche@techne.com.br
 * @since 06/05/2019
 *
 */
public interface ProjectSerializer {
	public byte [] serialize(Project project);
	public Project deserialize(byte [] data);
}
