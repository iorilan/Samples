// mfcLab1Doc.h : interface of the CmfcLab1Doc class
//


#pragma once


class CmfcLab1Doc : public CDocument
{
protected: // create from serialization only
	CmfcLab1Doc();
	DECLARE_DYNCREATE(CmfcLab1Doc)

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
	virtual ~CmfcLab1Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};


