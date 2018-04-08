// mfcLab2Doc.h : interface of the CmfcLab2Doc class
//


#pragma once


class CmfcLab2Doc : public CDocument
{
protected: // create from serialization only
	CmfcLab2Doc();
	DECLARE_DYNCREATE(CmfcLab2Doc)

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
	virtual ~CmfcLab2Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};


