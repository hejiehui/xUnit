package com.xrosstools.xunit;

public interface BiBranch extends Unit{
	void setValidator(Validator validator);
	void setValidUnit(Unit unit);
	void setInvalidUnit(Unit unit);
}
