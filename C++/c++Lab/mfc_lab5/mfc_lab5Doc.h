// mfc_lab5Doc.h : interface of the Cmfc_lab5Doc class
//


#pragma once


class Cmfc_lab5Doc : public CDocument
{
protected: // create from serialization only
	Cmfc_lab5Doc();
	DECLARE_DYNCREATE(Cmfc_lab5Doc)

// Attributes
public:

// Operations
public:

// Overrides
public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);

// Implementation
public:
	virtual ~Cmfc_lab5Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};


