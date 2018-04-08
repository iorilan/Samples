
#include "stdafx.h"
#include "MfcPropTest.h"
#include "ProPage2.h"


// ProPage2 dialog

IMPLEMENT_DYNAMIC(ProPage2, CPropertyPage)

ProPage2::ProPage2()
	: CPropertyPage(ProPage2::IDD)
{

}

BOOL ProPage2::OnSetActive(){
((CPropertySheet*)this->GetParent())->SetWizardButtons(PSWIZB_NEXT|PSWIZB_BACK);
return true;
}

ProPage2::~ProPage2()
{
}

void ProPage2::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(ProPage2, CPropertyPage)
END_MESSAGE_MAP()


// ProPage2 message handlers
