// mfc7Doc.h : interface of the Cmfc7Doc class
//


#pragma once


class Cmfc7Doc : public CDocument
{
protected: // create from serialization only
	Cmfc7Doc();
	DECLARE_DYNCREATE(Cmfc7Doc)

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
	virtual ~Cmfc7Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};


