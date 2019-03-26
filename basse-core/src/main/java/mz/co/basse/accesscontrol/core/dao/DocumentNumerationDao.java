package mz.co.basse.accesscontrol.core.dao;

import mz.co.basse.accesscontrol.core.model.DocumentNumeration;
import mz.co.basse.accesscontrol.core.model.DocumentNumerationType;

public interface DocumentNumerationDao {

	DocumentNumeration findDocumentNumeration(DocumentNumerationType type);

	DocumentNumeration findDocumentNumeration(DocumentNumerationType type,
			int year);

	void createOrUpdate(DocumentNumeration documentNumeration);

}
