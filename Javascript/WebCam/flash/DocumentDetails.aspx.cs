using System;
using System.Collections.Generic;
using CRMWeb.eDAS.Entities.SessionEntity;
using CRMWeb.eDAS.Presenters;
using CRMWeb.eDAS.Views;
using Microsoft.Practices.ObjectBuilder;
using CRMWeb.eDAS.Util;
using CRMWeb.eDAS.Constant;

namespace EdasWeb.DocumentDetails
{
    public partial class DocumentDetails : Microsoft.Practices.CompositeWeb.Web.UI.Page, IDocumentDetailsView
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                _presenter.OnViewInitialized();
            }
            _presenter.OnViewLoaded();
        }

        protected void btnDone_Click(object sender, EventArgs e)
        {
            _presenter.SnapshotDone();
        }

        protected void btnRetake_Click(object sender, EventArgs e)
        {
            Response.Redirect(PageUrls.CameraUrl);
        }

        protected void btnNew_Click(object sender, EventArgs e)
        {
            var current = EdasContext.DocumentSnapShots.Current;
            if(current.SnapShotDocuments == null)
                current.SnapShotDocuments = new List<SnapShotPhotos>();

            EdasContext.DocumentSnapShots.Current.SnapShotDocuments.Add(current.LastestTaken);
            EdasContext.DocumentSnapShots.Set(current);

            Response.Redirect(PageUrls.CameraUrl);
        }

        public void ShowAlertMsg(string msg)
        {
            EdasDialog.ShowAlert(msg);
        }

        private DocumentDetailsPresenter _presenter;

        [CreateNew]
        public DocumentDetailsPresenter Presenter
        {
            get
            {
                return _presenter;
            }
            set
            {
                if (value == null)
                    throw new ArgumentNullException("value");

                _presenter = value;
                _presenter.View = this;
            }
        }

        public string SelectedIdType { get { return ddlIdType.SelectedItem.Text; } }
        public string SelectedDocPage { get { return ddlDocPage.SelectedItem.Text; } }
        public void ShowPhoto()
        {
            capturedPhoto.ImageUrl =
                "data:image/jpg;base64," + Convert.ToBase64String(EdasContext.DocumentSnapShots.Current.LastestTaken.ImgStream);

        }
    }
}