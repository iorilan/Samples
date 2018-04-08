// mfclab6Doc.h : interface of the Cmfclab6Doc class
//


#pragma once


class Cmfclab6Doc : public CDocument
{
protected: // create from serialization only
	Cmfclab6Doc();
	DECLARE_DYNCREATE(Cmfclab6Doc)

// Attributes
public:
CPen pen;
// Operations
public:

// Overrides
public:
	
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);

// Implementation
public:
	virtual ~Cmfclab6Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};


