package pl.edu.pwr.psi.entrustmentswebservice.common.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ComplexModelMapper {

	@Autowired
	private ModelMapper modelMapper;

	public <D, S> List<D> mapAll(final Collection<S> sourceCollection, Class<D> destClass) {
		return sourceCollection.stream()
				.map(obj -> map(obj, destClass))
				.collect(Collectors.toList());
	}

	public <D, S> Page<D> mapPage(final Page<S> sourceCollection, Class<D> destClass) {
		return sourceCollection.map(obj -> map(obj, destClass));
	}

	public <D, S> D map(final S source, Class<D> destClass) {
		return modelMapper.map(source, destClass);
	}
}
